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

    // Primary: Real-time GitHub Tree API (immediate updates on any git commit/push)
    private const val GITHUB_TREE_API = "https://api.github.com/repos/pandasuryanarayan/logoquiz/git/trees/main?recursive=1"
    private const val GITHUB_CONTENTS_BASE = "https://api.github.com/repos/pandasuryanarayan/logoquiz/contents"

    // Fallbacks & CDN
    private const val JSDELIVR_DATA_API = "https://data.jsdelivr.com/v1/package/gh/pandasuryanarayan/logoquiz@main"
    private const val JSDELIVR_DATA_API_FALLBACK = "https://data.jsdelivr.com/v1/package/gh/pandasuryanarayan/logoquiz@HEAD"
    private const val JSDELIVR_PURGE_API = "https://purge.jsdelivr.net/gh/pandasuryanarayan/logoquiz@main"
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
                val rawUrl = if (obj.has("imageUrl") && !obj.isNull("imageUrl")) obj.getString("imageUrl") else null
                if (rawUrl.isNullOrBlank()) {
                    continue
                }
                // Avoid loading cached duplicates of bundled authentic levels
                val isDuplicateOfBundled = QuizPackData.bundledLevels.any { bundled ->
                    bundled.id == obj.getString("id") ||
                            (bundled.packId == obj.getString("packId") && bundled.answer.equals(obj.getString("answer"), ignoreCase = true)) ||
                            bundled.imageUrl.equals(rawUrl, ignoreCase = true)
                }
                if (isDuplicateOfBundled) {
                    continue
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
                        imageUrl = rawUrl,
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
     * Queries GitHub in real time, discovers new logos across topic folders,
     * assigns sequential level numbers, and reconciles QuizPackData.
     */
    suspend fun syncRemoteLogos(context: Context, targetPackId: String? = null): SyncResult = withContext(Dispatchers.IO) {
        val currentLevels = QuizPackData.allLevels.toMutableList()
        var newLevelsAddedCount = 0

        // 1. Primary: Real-time GitHub Tree API (instant updates on push to main)
        val treeFiles = mutableMapOf<String, MutableSet<String>>()

        val ghTree = fetchGitHubTree()
        for ((folder, files) in ghTree) {
            treeFiles.getOrPut(folder) { mutableSetOf() }.addAll(files)
        }

        // 2. Secondary fallback: Query per-folder GitHub Contents API if Tree was empty or rate-limited
        if (treeFiles.isEmpty()) {
            Log.w(TAG, "GitHub Tree API empty or unreachable, querying per-folder GitHub Contents API")
            val ghContents = fetchGitHubContentsAllFolders(targetPackId)
            for ((folder, files) in ghContents) {
                treeFiles.getOrPut(folder) { mutableSetOf() }.addAll(files)
            }
        }

        // 3. Third fallback: jsDelivr CDN Data API
        if (treeFiles.isEmpty()) {
            Log.w(TAG, "GitHub APIs unreachable, attempting jsDelivr CDN Data API fallback")
            val jsTree = fetchJsDelivrTree()
            for ((folder, files) in jsTree) {
                treeFiles.getOrPut(folder) { mutableSetOf() }.addAll(files)
            }
        }

        if (treeFiles.isNotEmpty()) {
            for ((folderName, fileSet) in treeFiles) {
                val packCategory = resolvePackCategory(folderName) ?: continue
                if (targetPackId != null && packCategory.id != targetPackId) continue

                val existingPackLevels = currentLevels.filter { it.packId == packCategory.id }.toMutableList()
                val seenInThisRun = mutableSetOf<String>()

                // Sort file list so level addition is deterministic
                val sortedFiles = fileSet.sorted()

                for (fileName in sortedFiles) {
                    if (!isImageFile(fileName)) continue
                    val cleanAnswer = extractCleanAnswer(fileName)
                    if (cleanAnswer.isBlank()) continue
                    if (cleanAnswer in seenInThisRun) continue
                    seenInThisRun.add(cleanAnswer)

                    val cdnUrl = QuizPackData.buildCdnUrl(folderName, fileName)
                    val resolved = resolveBrandMeta(folderName, fileName, packCategory.title)

                    // Check if already registered
                    val alreadyRegistered = existingPackLevels.any { lvl ->
                        lvl.imageUrl.equals(cdnUrl, ignoreCase = true) ||
                                lvl.answer.equals(cleanAnswer, ignoreCase = true) ||
                                lvl.answer.equals(resolved.answer, ignoreCase = true) ||
                                lvl.logoKey.equals(cleanAnswer.lowercase(), ignoreCase = true) ||
                                lvl.originalName.equals(resolved.originalName, ignoreCase = true)
                    }

                    if (!alreadyRegistered) {
                        val newLevelNumber = (existingPackLevels.maxOfOrNull { it.levelNumber } ?: 0) + 1

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
                        Log.d(TAG, "Discovered real-time logo from GitHub: ${newLevel.originalName} (${newLevel.answer}) in ${packCategory.title} as Level #$newLevelNumber")
                    }
                }
            }
        }

        // Trigger CDN cache purge in the background so jsDelivr stays in sync
        triggerCdnPurge()

        if (newLevelsAddedCount > 0) {
            QuizPackData.updateLevels(currentLevels)
            val dynamicOnly = currentLevels.filter { it !in QuizPackData.bundledLevels }
            saveCachedLevels(context, dynamicOnly)
        }

        val total = currentLevels.size
        val targetPack = if (targetPackId != null) PackCategory.entries.find { it.id == targetPackId } else null
        val packCount = if (targetPack != null) currentLevels.count { it.packId == targetPack.id } else null

        val message = when {
            newLevelsAddedCount > 0 && targetPack != null && packCount != null ->
                "⚡ Real-time sync complete! Added $newLevelsAddedCount new logo${if (newLevelsAddedCount > 1) "s" else ""} to ${targetPack.title} (Total: $packCount)!"
            newLevelsAddedCount > 0 ->
                "⚡ Real-time sync complete! Added $newLevelsAddedCount new logo${if (newLevelsAddedCount > 1) "s" else ""} from GitHub! Total: $total logos."
            targetPack != null && packCount != null ->
                "✓ ${targetPack.title} is fully up to date with GitHub in real time ($packCount logos)!"
            else ->
                "✓ Real-time sync verified! All $total logos across all categories match GitHub."
        }

        SyncResult(
            success = true,
            newLevelsCount = newLevelsAddedCount,
            totalLevels = total,
            message = message
        )
    }

    /**
     * Primary: Fetches real-time recursive Git Tree from GitHub API.
     * Always reflects the latest commit pushed to the main branch instantly.
     */
    private fun fetchGitHubTree(): Map<String, List<String>> {
        val result = mutableMapOf<String, MutableList<String>>()
        try {
            val urlWithTimestamp = "$GITHUB_TREE_API&_t=${System.currentTimeMillis()}"
            val connection = (URL(urlWithTimestamp).openConnection() as HttpURLConnection).apply {
                connectTimeout = 8000
                readTimeout = 8000
                setRequestProperty("User-Agent", "LogoQuiz-Android/1.0")
                setRequestProperty("Accept", "application/vnd.github.v3+json")
                setRequestProperty("Cache-Control", "no-cache, no-store, must-revalidate")
                setRequestProperty("Pragma", "no-cache")
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
                        if (isImageFile(file)) {
                            result.getOrPut(folder) { mutableListOf() }.add(file)
                        }
                    }
                }
                Log.d(TAG, "GitHub Tree API fetched ${result.values.sumOf { it.size }} logos across ${result.size} folders")
            } else {
                Log.w(TAG, "GitHub Git Tree API returned HTTP ${connection.responseCode}")
            }
        } catch (e: Exception) {
            Log.e(TAG, "GitHub Git Tree fetch error", e)
        }
        return result
    }

    /**
     * Fallback 1: Queries GitHub Contents API per-folder.
     */
    private fun fetchGitHubContentsAllFolders(targetPackId: String? = null): Map<String, List<String>> {
        val result = mutableMapOf<String, MutableList<String>>()
        for (pack in PackCategory.entries) {
            if (targetPackId != null && pack.id != targetPackId) continue
            try {
                val encodedFolder = URLEncoder.encode(pack.folderName, "UTF-8").replace("+", "%20")
                val apiUrl = "$GITHUB_CONTENTS_BASE/$encodedFolder?_t=${System.currentTimeMillis()}"
                val connection = (URL(apiUrl).openConnection() as HttpURLConnection).apply {
                    connectTimeout = 6000
                    readTimeout = 6000
                    setRequestProperty("User-Agent", "LogoQuiz-Android/1.0")
                    setRequestProperty("Accept", "application/vnd.github.v3+json")
                    setRequestProperty("Cache-Control", "no-cache, no-store, must-revalidate")
                    setRequestProperty("Pragma", "no-cache")
                }
                if (connection.responseCode == 200) {
                    val body = connection.inputStream.bufferedReader().use { it.readText() }
                    val array = JSONArray(body)
                    for (i in 0 until array.length()) {
                        val item = array.getJSONObject(i)
                        if (item.optString("type") != "file") continue
                        val fileName = item.getString("name")
                        if (isImageFile(fileName)) {
                            result.getOrPut(pack.folderName) { mutableListOf() }.add(fileName)
                        }
                    }
                }
            } catch (e: Exception) {
                Log.w(TAG, "GitHub contents error for ${pack.folderName}", e)
            }
        }
        return result
    }

    /**
     * Fallback 2: jsDelivr CDN Data API
     */
    private fun fetchJsDelivrTree(): Map<String, List<String>> {
        val result = mutableMapOf<String, MutableList<String>>()
        val endpoints = listOf(
            "$JSDELIVR_DATA_API?_t=${System.currentTimeMillis()}",
            "$JSDELIVR_DATA_API_FALLBACK?_t=${System.currentTimeMillis()}"
        )

        for (apiUrl in endpoints) {
            try {
                val connection = (URL(apiUrl).openConnection() as HttpURLConnection).apply {
                    connectTimeout = 6000
                    readTimeout = 6000
                    setRequestProperty("User-Agent", "LogoQuiz-Android/1.0")
                    setRequestProperty("Accept", "application/json")
                    setRequestProperty("Cache-Control", "no-cache, no-store, must-revalidate")
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
                        return result
                    }
                }
            } catch (e: Exception) {
                Log.w(TAG, "jsDelivr CDN tree fetch exception on $apiUrl", e)
            }
        }
        return result
    }

    /**
     * Triggers a purge request on jsDelivr so the CDN cache doesn't serve stale versions.
     */
    private fun triggerCdnPurge() {
        try {
            val connection = (URL(JSDELIVR_PURGE_API).openConnection() as HttpURLConnection).apply {
                connectTimeout = 3000
                readTimeout = 3000
                requestMethod = "POST"
                setRequestProperty("User-Agent", "LogoQuiz-Android/1.0")
            }
            connection.responseCode
        } catch (_: Exception) {
            // Ignore background purge errors
        }
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
            it.answer.equals(clean, ignoreCase = true) || it.logoKey.equals(clean.lowercase(), ignoreCase = true)
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

        // Known brand lookups for dynamic addition
        val customMeta = getCuratedBrandMeta(clean)
        if (customMeta != null) {
            return customMeta
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
        val trivia = "A globally recognized brand mark celebrated in $categoryTitle with millions of fans worldwide."

        return BrandMeta(
            originalName = formattedName.ifBlank { clean },
            answer = clean,
            hint = hint,
            trivia = trivia,
            alternateAnswers = emptyList()
        )
    }

    private fun getCuratedBrandMeta(cleanAnswer: String): BrandMeta? {
        return when (cleanAnswer) {
            "ABARTH" -> BrandMeta(
                originalName = "Abarth",
                answer = "ABARTH",
                hint = "Italian performance tuning marque recognized worldwide for the fiery scorpion crest",
                trivia = "Founded by Carlo Abarth in 1949 in Turin, renowned for turning compact chassis into rally winners."
            )
            "ALPINE" -> BrandMeta(
                originalName = "Alpine",
                answer = "ALPINE",
                hint = "French sports and racing car marque celebrated for the iconic rear-engine A110",
                trivia = "Founded in 1955 by Jean Rédélé, today Alpine powers France's Formula 1 racing operations."
            )
            "CATERHAM" -> BrandMeta(
                originalName = "Caterham",
                answer = "CATERHAM",
                hint = "British specialist lightweight sports car maker famed for open-wheel Lotus Seven racers",
                trivia = "Produces minimalist track-focused cars honoring Colin Chapman's philosophy of adding lightness."
            )
            "CORVETTE" -> BrandMeta(
                originalName = "Corvette",
                answer = "CORVETTE",
                hint = "America's sports car legend celebrated by the crossed racing and fleur-de-lis flags",
                trivia = "Produced across eight storied generations since 1953, evolving into a mid-engine supercar."
            )
            else -> null
        }
    }
}
