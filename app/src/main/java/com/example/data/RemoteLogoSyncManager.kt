package com.example.data

import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

object RemoteLogoSyncManager {

    private const val TAG = "RemoteLogoSync"
    private const val PREFS_NAME = "logo_quiz_remote_sync"
    private const val KEY_CACHED_REMOTE_LEVELS = "cached_remote_levels_json"

    // Primary: jsDelivr Data API fetches the directory and file tree of the package
    private const val JSDELIVR_DATA_API = "https://data.jsdelivr.com/v1/package/gh/pandasuryanarayan/logoquiz@main"
    private const val JSDELIVR_DATA_API_FALLBACK = "https://data.jsdelivr.com/v1/package/gh/pandasuryanarayan/logoquiz@HEAD"
    private const val GITHUB_TREE_API = "https://api.github.com/repos/pandasuryanarayan/logoquiz/git/trees/main?recursive=1"
    private const val GITHUB_CONTENTS_BASE = "https://api.github.com/repos/pandasuryanarayan/logoquiz/contents"
    const val CDN_BASE_URL = "https://cdn.jsdelivr.net/gh/pandasuryanarayan/logoquiz"

    data class SyncResult(
        val success: Boolean,
        val newLevelsCount: Int,
        val totalLevels: Int,
        val message: String
    )

    /**
     * Loads any previously persisted remote levels from SharedPreferences into QuizPackData.
     */
    fun loadCachedLevels(context: Context): List<QuizLevel> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val jsonString = prefs.getString(KEY_CACHED_REMOTE_LEVELS, null) ?: return emptyList()
        return try {
            val jsonArray = JSONArray(jsonString)
            val list = mutableListOf<QuizLevel>()
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                val alternates = mutableListOf<String>()
                val altArray = obj.optJSONArray("alternateAnswers")
                if (altArray != null) {
                    for (j in 0 until altArray.length()) {
                        alternates.add(altArray.getString(j))
                    }
                }
                list.add(
                    QuizLevel(
                        id = obj.getString("id"),
                        packId = obj.getString("packId"),
                        levelNumber = obj.getInt("levelNumber"),
                        answer = obj.getString("answer"),
                        hintSentence = obj.getString("hintSentence"),
                        triviaFact = obj.getString("triviaFact"),
                        logoKey = obj.getString("logoKey"),
                        imageUrl = if (obj.has("imageUrl") && !obj.isNull("imageUrl")) obj.getString("imageUrl") else null,
                        originalName = obj.optString("originalName", obj.getString("answer")),
                        alternateAnswers = alternates
                    )
                )
            }
            list
        } catch (e: Exception) {
            Log.e(TAG, "Failed to parse cached remote levels", e)
            emptyList()
        }
    }

    private fun saveCachedLevels(context: Context, levels: List<QuizLevel>) {
        try {
            val jsonArray = JSONArray()
            levels.forEach { level ->
                val obj = JSONObject()
                obj.put("id", level.id)
                obj.put("packId", level.packId)
                obj.put("levelNumber", level.levelNumber)
                obj.put("answer", level.answer)
                obj.put("hintSentence", level.hintSentence)
                obj.put("triviaFact", level.triviaFact)
                obj.put("logoKey", level.logoKey)
                obj.put("imageUrl", level.imageUrl)
                obj.put("originalName", level.originalName)
                val altArray = JSONArray()
                level.alternateAnswers.forEach { altArray.put(it) }
                obj.put("alternateAnswers", altArray)
                jsonArray.put(obj)
            }
            val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            prefs.edit().putString(KEY_CACHED_REMOTE_LEVELS, jsonArray.toString()).apply()
        } catch (e: Exception) {
            Log.e(TAG, "Failed to save cached remote levels", e)
        }
    }

    /**
     * Queries jsDelivr CDN in real time, discovers logos across all topic folders,
     * assigns sequential level numbers, and reconciles QuizPackData.
     */
    suspend fun syncRemoteLogos(context: Context, targetPackId: String? = null): SyncResult = withContext(Dispatchers.IO) {
        val currentLevels = QuizPackData.allLevels.toMutableList()
        var newLevelsAddedCount = 0

        // 1. Primary: Query jsDelivr CDN Package Data API directly
        var treeFiles = fetchJsDelivrTree()

        // 2. Secondary fallback: Git Trees recursive API if jsDelivr Data API was unreachable
        if (treeFiles.isEmpty()) {
            Log.w(TAG, "jsDelivr tree empty or unreachable, attempting Git Tree fallback")
            treeFiles = fetchGitHubTree()
        }

        if (treeFiles.isNotEmpty()) {
            for ((folderName, fileList) in treeFiles) {
                val packCategory = resolvePackCategory(folderName) ?: continue
                if (targetPackId != null && packCategory.id != targetPackId) continue

                val existingPackLevels = currentLevels.filter { it.packId == packCategory.id }.toMutableList()
                val seenInThisRun = mutableSetOf<String>()

                for (fileName in fileList) {
                    if (!isImageFile(fileName)) continue
                    val cleanAnswer = extractCleanAnswer(fileName)
                    if (cleanAnswer.isBlank()) continue
                    if (cleanAnswer in seenInThisRun) continue
                    seenInThisRun.add(cleanAnswer)

                    val cdnUrl = QuizPackData.buildCdnUrl(folderName, fileName)

                    // Check if already registered
                    val alreadyRegistered = existingPackLevels.any { lvl ->
                        lvl.imageUrl == cdnUrl ||
                                lvl.answer.equals(cleanAnswer, ignoreCase = true) ||
                                lvl.logoKey.equals(cleanAnswer, ignoreCase = true)
                    }

                    if (!alreadyRegistered) {
                        val newLevelNumber = (existingPackLevels.maxOfOrNull { it.levelNumber } ?: 0) + 1
                        val resolved = resolveBrandMeta(folderName, fileName, packCategory.title)

                        val newLevel = QuizLevel(
                            id = "${packCategory.id}_$newLevelNumber",
                            packId = packCategory.id,
                            levelNumber = newLevelNumber,
                            answer = resolved.answer,
                            hintSentence = resolved.hint,
                            triviaFact = resolved.trivia,
                            logoKey = cleanAnswer.lowercase(),
                            imageUrl = cdnUrl,
                            originalName = resolved.originalName,
                            alternateAnswers = resolved.alternateAnswers
                        )

                        existingPackLevels.add(newLevel)
                        currentLevels.add(newLevel)
                        newLevelsAddedCount++
                        Log.d(TAG, "Discovered new logo from jsDelivr CDN: ${newLevel.originalName} (${newLevel.answer}) in ${packCategory.title}")
                    }
                }
            }
        } else {
            // Fallback to per-folder contents endpoint if both jsDelivr and Git Tree APIs are unavailable
            Log.w(TAG, "Both jsDelivr and Git Tree APIs returned 0 items, falling back to contents endpoint")
            for (pack in PackCategory.entries) {
                if (targetPackId != null && pack.id != targetPackId) continue
                try {
                    val encodedFolder = URLEncoder.encode(pack.folderName, "UTF-8").replace("+", "%20")
                    val apiUrl = "$GITHUB_CONTENTS_BASE/$encodedFolder"
                    val connection = (URL(apiUrl).openConnection() as HttpURLConnection).apply {
                        connectTimeout = 6000
                        readTimeout = 6000
                        setRequestProperty("User-Agent", "LogoQuiz-Android")
                        setRequestProperty("Accept", "application/vnd.github.v3+json")
                    }
                    if (connection.responseCode == 200) {
                        val body = connection.inputStream.bufferedReader().use { it.readText() }
                        val array = JSONArray(body)
                        val existingPackLevels = currentLevels.filter { it.packId == pack.id }.toMutableList()

                        for (i in 0 until array.length()) {
                            val item = array.getJSONObject(i)
                            if (item.optString("type") != "file") continue
                            val fileName = item.getString("name")
                            if (!isImageFile(fileName)) continue

                            val cleanAnswer = extractCleanAnswer(fileName)
                            if (cleanAnswer.isBlank()) continue

                            val cdnUrl = QuizPackData.buildCdnUrl(pack.folderName, fileName)
                            val exists = existingPackLevels.any { lvl ->
                                lvl.imageUrl == cdnUrl || lvl.answer.equals(cleanAnswer, ignoreCase = true)
                            }
                            if (!exists) {
                                val nextNum = (existingPackLevels.maxOfOrNull { it.levelNumber } ?: 0) + 1
                                val resolved = resolveBrandMeta(pack.folderName, fileName, pack.title)
                                val newLevel = QuizLevel(
                                    id = "${pack.id}_$nextNum",
                                    packId = pack.id,
                                    levelNumber = nextNum,
                                    answer = resolved.answer,
                                    hintSentence = resolved.hint,
                                    triviaFact = resolved.trivia,
                                    logoKey = cleanAnswer.lowercase(),
                                    imageUrl = cdnUrl,
                                    originalName = resolved.originalName,
                                    alternateAnswers = resolved.alternateAnswers
                                )
                                existingPackLevels.add(newLevel)
                                currentLevels.add(newLevel)
                                newLevelsAddedCount++
                            }
                        }
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Fallback contents error for ${pack.folderName}", e)
                }
            }
        }

        if (newLevelsAddedCount > 0) {
            QuizPackData.updateLevels(currentLevels)
            val dynamicOnly = currentLevels.filter { it !in QuizPackData.bundledLevels }
            saveCachedLevels(context, dynamicOnly)
        }

        val total = currentLevels.size
        val message = if (newLevelsAddedCount > 0) {
            "Synced $newLevelsAddedCount new real logo${if (newLevelsAddedCount > 1) "s" else ""} from jsDelivr CDN!"
        } else {
            "All logos from jsDelivr CDN are synced and up to date."
        }

        SyncResult(
            success = true,
            newLevelsCount = newLevelsAddedCount,
            totalLevels = total,
            message = message
        )
    }

    private fun fetchJsDelivrTree(): Map<String, List<String>> {
        val result = mutableMapOf<String, MutableList<String>>()
        val urlsToTry = listOf(JSDELIVR_DATA_API, JSDELIVR_DATA_API_FALLBACK)

        for (apiUrl in urlsToTry) {
            try {
                val connection = (URL(apiUrl).openConnection() as HttpURLConnection).apply {
                    connectTimeout = 7000
                    readTimeout = 7000
                    setRequestProperty("User-Agent", "LogoQuiz-Android")
                    setRequestProperty("Accept", "application/json")
                }
                if (connection.responseCode == 200) {
                    val body = connection.inputStream.bufferedReader().use { it.readText() }
                    val root = JSONObject(body)
                    val filesArray = root.optJSONArray("files") ?: continue

                    for (i in 0 until filesArray.length()) {
                        val entry = filesArray.getJSONObject(i)
                        val type = entry.optString("type")
                        val folderName = entry.optString("name")
                        if (type == "directory") {
                            val subFiles = entry.optJSONArray("files") ?: continue
                            for (j in 0 until subFiles.length()) {
                                val fileItem = subFiles.getJSONObject(j)
                                if (fileItem.optString("type") == "file") {
                                    val fileName = fileItem.getString("name")
                                    if (isImageFile(fileName)) {
                                        result.getOrPut(folderName) { mutableListOf() }.add(fileName)
                                    }
                                }
                            }
                        }
                    }

                    if (result.isNotEmpty()) {
                        Log.d(TAG, "Fetched ${result.values.sumOf { it.size }} logos across ${result.size} categories from jsDelivr CDN")
                        return result
                    }
                } else {
                    Log.w(TAG, "jsDelivr CDN API returned HTTP ${connection.responseCode} on $apiUrl")
                }
            } catch (e: Exception) {
                Log.w(TAG, "jsDelivr CDN tree fetch exception on $apiUrl", e)
            }
        }
        return result
    }

    private fun fetchGitHubTree(): Map<String, List<String>> {
        val result = mutableMapOf<String, MutableList<String>>()
        try {
            val connection = (URL(GITHUB_TREE_API).openConnection() as HttpURLConnection).apply {
                connectTimeout = 7000
                readTimeout = 7000
                setRequestProperty("User-Agent", "LogoQuiz-Android")
                setRequestProperty("Accept", "application/vnd.github.v3+json")
            }
            if (connection.responseCode == 200) {
                val body = connection.inputStream.bufferedReader().use { it.readText() }
                val root = JSONObject(body)
                val tree = root.optJSONArray("tree") ?: return emptyMap()

                for (i in 0 until tree.length()) {
                    val item = tree.getJSONObject(i)
                    val type = item.optString("type")
                    if (type != "blob") continue
                    val path = item.getString("path")
                    if ("/" in path) {
                        val folder = path.substringBefore("/")
                        val file = path.substringAfterLast("/")
                        result.getOrPut(folder) { mutableListOf() }.add(file)
                    }
                }
            } else {
                Log.w(TAG, "Git tree API returned HTTP ${connection.responseCode}")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Git tree fetch error", e)
        }
        return result
    }

    private fun resolvePackCategory(folderName: String): PackCategory? {
        val cleanFolder = folderName.replace("&", "and").replace(" ", "").lowercase()
        return PackCategory.entries.find { cat ->
            val cleanCatFolder = cat.folderName.replace("&", "and").replace(" ", "").lowercase()
            val cleanCatTitle = cat.title.replace("&", "and").replace(" ", "").lowercase()
            cleanCatFolder == cleanFolder || cleanCatTitle == cleanFolder
        }
    }

    private fun isImageFile(fileName: String): Boolean {
        val lower = fileName.lowercase()
        return lower.endsWith(".webp") || lower.endsWith(".png") || lower.endsWith(".jpg") ||
                lower.endsWith(".jpeg") || lower.endsWith(".svg")
    }

    fun extractCleanAnswer(fileName: String): String {
        val base = fileName.substringBeforeLast(".")
        return base.filter { it.isLetter() }.uppercase()
    }

    private data class BrandMeta(
        val originalName: String,
        val answer: String,
        val hint: String,
        val trivia: String,
        val alternateAnswers: List<String> = emptyList()
    )

    private fun resolveBrandMeta(folderName: String, fileName: String, categoryTitle: String): BrandMeta {
        val rawBase = fileName.substringBeforeLast(".")
        val clean = extractCleanAnswer(fileName)

        // Check if bundled level exists for this brand
        val existingBundled = QuizPackData.bundledLevels.find {
            it.answer.equals(clean, ignoreCase = true) || it.logoKey.equals(clean, ignoreCase = true)
        }
        if (existingBundled != null) {
            return BrandMeta(
                originalName = existingBundled.originalName,
                answer = existingBundled.answer,
                hint = existingBundled.hintSentence,
                trivia = existingBundled.triviaFact,
                alternateAnswers = existingBundled.alternateAnswers
            )
        }

        // Format clean original name (e.g. "alfa-romeo" -> "Alfa Romeo", "taco bell" -> "Taco Bell")
        val formattedName = rawBase
            .replace("-", " ")
            .replace("_", " ")
            .split(" ")
            .filter { it.isNotBlank() }
            .joinToString(" ") { word ->
                word.lowercase().replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
            }

        val hint = "Iconic brand in $categoryTitle recognized worldwide"
        val trivia = "A globally recognized brand mark celebrated in $categoryTitle with millions of daily users."

        return BrandMeta(
            originalName = formattedName.ifBlank { clean },
            answer = clean,
            hint = hint,
            trivia = trivia,
            alternateAnswers = emptyList()
        )
    }
}
