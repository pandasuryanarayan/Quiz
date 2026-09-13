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

    private const val GITHUB_API_BASE = "https://api.github.com/repos/pandasuryanarayan/logoquiz/contents"
    private const val CDN_BASE_URL = "https://cdn.jsdelivr.net/gh/pandasuryanarayan/logoquiz"

    data class SyncResult(
        val success: Boolean,
        val newLevelsCount: Int,
        val totalLevels: Int,
        val message: String
    )

    data class FolderMapping(
        val folderName: String,
        val packId: String
    )

    private val FOLDERS = listOf(
        FolderMapping("Famous Brands", "brands"),
        FolderMapping("Entertainment", "entertainment"),
        FolderMapping("Food & Treats", "food"),
        FolderMapping("Sports & Autos", "sports")
    )

    // Curated catalog for famous brands so any newly uploaded logo gets high-quality answers & trivia
    private val BRAND_KNOWLEDGE = mapOf(
        "microsoft" to BrandMeta("MICROSOFT", "Global software giant famous for Windows, Office, and Xbox", "Founded in 1975 by Bill Gates and Paul Allen, Microsoft's name is a portmanteau of 'microcomputer' and 'software'."),
        "samsung" to BrandMeta("SAMSUNG", "South Korean tech titan famous for Galaxy phones, memory chips, and smart TVs", "In Korean, 'Samsung' means 'three stars', symbolizing big, numerous, and powerful."),
        "intel" to BrandMeta("INTEL", "Semiconductor pioneer known for processors powering personal computers worldwide", "The name Intel is a portmanteau of 'Integrated Electronics', founded in 1968 by Robert Noyce and Gordon Moore."),
        "sony" to BrandMeta("SONY", "Japanese entertainment and tech giant famous for PlayStation, cameras, and audio", "The name Sony comes from the Latin word 'sonus' (sound) and the slang term 'sonny boy'."),
        "adobe" to BrandMeta("ADOBE", "Creative software powerhouse behind Photoshop, Acrobat, and Illustrator", "Adobe was named after Adobe Creek in Los Altos, California, which ran behind co-founder John Warnock's house."),
        "starbucks" to BrandMeta("STARBUCKS", "Global coffeehouse chain with a twin-tailed siren emblem", "The Starbucks siren is named after the legendary twin-tailed creature from 16th-century Norse mythology."),
        "twitter" to BrandMeta("TWITTER", "Social microblogging platform recognized by a soaring blue bird", "Twitter's original bird logo was named 'Larry' in honor of NBA Hall of Famer Larry Bird."),
        "visa" to BrandMeta("VISA", "Global payments technology network connecting consumers and merchants", "The name VISA was chosen because it sounds the same and is recognizable in dozens of languages."),
        "mastercard" to BrandMeta("MASTERCARD", "Financial services giant with interlocking red and yellow circles", "Originally known as Master Charge: The Interbank Card before adopting Mastercard in 1979."),
        "ebay" to BrandMeta("EBAY", "Pioneering online auction and shopping marketplace", "The first item ever sold on eBay was a broken laser pointer for $14.83 in 1995."),
        "marvel" to BrandMeta("MARVEL", "Superhero comic powerhouse with a bold red badge", "Marvel was originally launched in 1939 as Timely Publications before adopting Marvel Comics."),
        "hbo" to BrandMeta("HBO", "Prestige cable and streaming pioneer with a static circle in its O", "Home Box Office launched in November 1972, broadcasting an NHL hockey game to 365 subscribers."),
        "paramount" to BrandMeta("PARAMOUNT", "Legendary film studio with a mountain peak encircled by stars", "Paramount Pictures was founded in 1912, making it the second-oldest surviving film studio in the US."),
        "pixar" to BrandMeta("PIXAR", "Pioneering CGI animation studio featuring a hopping desk lamp", "The playful desk lamp is named Luxo Jr., starring in Pixar's groundbreaking 1986 computer short."),
        "hulu" to BrandMeta("HULU", "Popular streaming service known for award-winning original dramas and comedies", "The name Hulu comes from two Mandarin Chinese proverbs relating to 'holder of precious things'."),
        "universal" to BrandMeta("UNIVERSAL", "Historic movie studio recognized by a revolving globe of planet Earth", "Universal Pictures was founded in 1912 by Carl Laemmle and is one of the original 'Big Five' studios."),
        "dreamworks" to BrandMeta("DREAMWORKS", "Animation studio famous for Shrek and a boy fishing from a crescent moon", "Founded in 1994 by Steven Spielberg, Jeffrey Katzenberg, and David Geffen (the 'SKG')."),
        "crunchyroll" to BrandMeta("CRUNCHYROLL", "Leading global anime streaming service with an orange eye emblem", "Crunchyroll hosts the world's largest anime streaming library with over 1,000 titles."),
        "subway" to BrandMeta("SUBWAY", "Fresh sub sandwich chain with green and yellow directional arrows", "Subway serves more than 5,300 sandwiches every minute across more than 37,000 global restaurants."),
        "pringles" to BrandMeta("PRINGLES", "Stackable potato crisp can featuring a mustachioed mascot named Julius", "The shape of a Pringle is mathematically known as a hyperbolic paraboloid, engineered to resist breakage."),
        "oreo" to BrandMeta("OREO", "World's favorite sandwich cookie with embossed chocolate wafers", "Over 500 billion Oreo cookies have been produced since their introduction in 1912 in New York City."),
        "nutella" to BrandMeta("NUTELLA", "Famous cocoa and hazelnut breakfast spread in a distinctive jar", "One jar of Nutella is sold somewhere in the world every 2.5 seconds, using 25% of global hazelnuts."),
        "kitkat" to BrandMeta("KITKAT", "Crisp wafer fingers coated in smooth milk chocolate: 'Have a break'", "KitKat was invented by Rowntree's in York, England in 1935 as Rowntree's Chocolate Crisp."),
        "doritos" to BrandMeta("DORITOS", "Flavored tortilla chips famous for triangular shape and bold Nacho Cheese", "Doritos were invented in 1966 at Casa de Fritos restaurant located inside Disneyland."),
        "pizzahut" to BrandMeta("PIZZAHUT", "Global pizza franchise famous for its iconic red roof logo and Pan Pizza", "Founded in 1958 in Wichita, Kansas by brothers Dan and Frank Carney with just $600."),
        "wendys" to BrandMeta("WENDYS", "Fast-food burger chain famous for fresh square patties and Frosty desserts", "Wendy's famous square burger patties were created so the meat hangs over the bun edges."),
        "snickers" to BrandMeta("SNICKERS", "Nougat, peanuts, and caramel candy bar: 'You're not you when you're hungry'", "Introduced in 1930 by Frank Mars, the candy bar was named after the Mars family's favorite horse."),
        "porsche" to BrandMeta("PORSCHE", "German sports car manufacturer featuring Stuttgart's crest and prancing horse", "The Porsche crest is based on the coat of arms of the Free People's State of Württemberg."),
        "lamborghini" to BrandMeta("LAMBORGHINI", "Italian luxury sports car maker with a charging golden bull emblem", "Ferruccio Lamborghini chose a charging bull because his astrological sign was Taurus."),
        "toyota" to BrandMeta("TOYOTA", "Japanese auto giant with three overlapping ellipses forming a T", "The three ovals represent the heart of the customer, the heart of the product, and endless progress."),
        "honda" to BrandMeta("HONDA", "Automotive and motorcycle leader with a bold silver H within a badge", "Honda is the world's largest manufacturer of internal combustion engines and motorcycles."),
        "underarmour" to BrandMeta("UNDERARMOUR", "Athletic apparel brand with an interlocking U and A emblem", "Founded in 1996 by former University of Maryland football player Kevin Plank in his grandmother's basement."),
        "nfl" to BrandMeta("NFL", "America's premier professional football league with a shield and eight stars", "The eight stars in the modern NFL shield represent the league's eight competitive divisions."),
        "harley" to BrandMeta("HARLEY", "Legendary American motorcycle manufacturer with an iconic Bar and Shield", "Harley-Davidson was founded in Milwaukee, Wisconsin in 1903 in a small 10x15 foot wooden shed.")
    )

    private data class BrandMeta(
        val answer: String,
        val hint: String,
        val trivia: String
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
                list.add(
                    QuizLevel(
                        id = obj.getString("id"),
                        packId = obj.getString("packId"),
                        levelNumber = obj.getInt("levelNumber"),
                        answer = obj.getString("answer"),
                        hintSentence = obj.getString("hintSentence"),
                        triviaFact = obj.getString("triviaFact"),
                        logoKey = obj.getString("logoKey"),
                        imageUrl = obj.optString("imageUrl", null)
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
                jsonArray.put(obj)
            }
            val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            prefs.edit().putString(KEY_CACHED_REMOTE_LEVELS, jsonArray.toString()).apply()
        } catch (e: Exception) {
            Log.e(TAG, "Failed to save cached remote levels", e)
        }
    }

    /**
     * Queries GitHub API in real time, discovers newly added logo files,
     * assigns sequential level numbers, and reconciles QuizPackData.
     */
    suspend fun syncRemoteLogos(context: Context, targetPackId: String? = null): SyncResult = withContext(Dispatchers.IO) {
        val currentLevels = QuizPackData.allLevels.toMutableList()
        var newLevelsAddedCount = 0

        val foldersToSync = if (targetPackId != null) {
            FOLDERS.filter { it.packId == targetPackId }
        } else {
            FOLDERS
        }

        for (mapping in foldersToSync) {
            try {
                val encodedFolder = URLEncoder.encode(mapping.folderName, "UTF-8").replace("+", "%20")
                val apiUrl = "$GITHUB_API_BASE/$encodedFolder"

                val connection = (URL(apiUrl).openConnection() as HttpURLConnection).apply {
                    connectTimeout = 7000
                    readTimeout = 7000
                    setRequestProperty("User-Agent", "LogoQuiz-Android")
                    setRequestProperty("Accept", "application/vnd.github.v3+json")
                }

                val responseCode = connection.responseCode
                if (responseCode == 200) {
                    val body = connection.inputStream.bufferedReader().use { it.readText() }
                    val array = JSONArray(body)

                    val existingPackLevels = currentLevels.filter { it.packId == mapping.packId }.toMutableList()

                    for (i in 0 until array.length()) {
                        val item = array.getJSONObject(i)
                        val type = item.optString("type")
                        if (type != "file") continue

                        val fileName = item.getString("name")
                        if (!isImageFile(fileName)) continue

                        val encodedFileName = URLEncoder.encode(fileName, "UTF-8").replace("+", "%20")
                        val cdnUrl = "$CDN_BASE_URL/$encodedFolder/$encodedFileName"

                        // Check if already registered
                        val alreadyRegistered = existingPackLevels.any { lvl ->
                            lvl.imageUrl == cdnUrl || isSameBrand(lvl, fileName)
                        }

                        if (!alreadyRegistered) {
                            // Discover & build new level
                            val newLevelNumber = (existingPackLevels.maxOfOrNull { it.levelNumber } ?: 0) + 1
                            val brandInfo = resolveBrandMeta(fileName, mapping.packId)

                            val newLevel = QuizLevel(
                                id = "${mapping.packId}_$newLevelNumber",
                                packId = mapping.packId,
                                levelNumber = newLevelNumber,
                                answer = brandInfo.answer,
                                hintSentence = brandInfo.hint,
                                triviaFact = brandInfo.trivia,
                                logoKey = brandInfo.logoKey,
                                imageUrl = cdnUrl
                            )

                            existingPackLevels.add(newLevel)
                            currentLevels.add(newLevel)
                            newLevelsAddedCount++
                            Log.d(TAG, "Discovered new level: ${newLevel.id} (${newLevel.answer}) in pack ${mapping.packId}")
                        }
                    }
                } else {
                    Log.w(TAG, "GitHub API returned $responseCode for folder ${mapping.folderName}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Failed syncing folder ${mapping.folderName}", e)
            }
        }

        if (newLevelsAddedCount > 0) {
            // Update in-memory dynamic levels list
            QuizPackData.updateLevels(currentLevels)
            // Persist newly discovered remote levels
            val dynamicOnly = currentLevels.filter { it !in QuizPackData.bundledLevels }
            saveCachedLevels(context, dynamicOnly)
        }

        val total = currentLevels.size
        val message = if (newLevelsAddedCount > 0) {
            "Found $newLevelsAddedCount new logo${if (newLevelsAddedCount > 1) "s" else ""}! Pack updated."
        } else {
            "All logos are up to date."
        }

        SyncResult(
            success = true,
            newLevelsCount = newLevelsAddedCount,
            totalLevels = total,
            message = message
        )
    }

    private fun isImageFile(fileName: String): Boolean {
        val lower = fileName.lowercase()
        return lower.endsWith(".webp") || lower.endsWith(".png") || lower.endsWith(".jpg") ||
                lower.endsWith(".jpeg") || lower.endsWith(".svg")
    }

    private fun isSameBrand(level: QuizLevel, fileName: String): Boolean {
        val cleanName = cleanBrandName(fileName).lowercase()
        return level.answer.lowercase() == cleanName || level.logoKey.lowercase() == cleanName
    }

    private fun cleanBrandName(fileName: String): String {
        var base = fileName.substringBeforeLast(".")
        base = base.replace("-logo", "", ignoreCase = true)
        base = base.replace("_logo", "", ignoreCase = true)
        base = base.replace(".wine", "", ignoreCase = true)
        base = base.replace("--streamline-simple-icons", "", ignoreCase = true)
        base = base.replace("_bullseye", "", ignoreCase = true)
        base = base.replace("_rings_without_rims", "", ignoreCase = true)
        base = base.replace(",_inc.-logomark-black-logo", "", ignoreCase = true)
        base = base.replace("_corporation-logo", "", ignoreCase = true)
        base = base.replace(" ", "")
        base = base.replace("_", "")
        base = base.replace("-", "")
        base = base.replace("'", "")
        base = base.replace("+", "")
        return base.filter { it.isLetter() }
    }

    private data class ResolvedBrand(
        val answer: String,
        val hint: String,
        val trivia: String,
        val logoKey: String
    )

    private fun resolveBrandMeta(fileName: String, packId: String): ResolvedBrand {
        val clean = cleanBrandName(fileName).lowercase()
        val meta = BRAND_KNOWLEDGE[clean]

        return if (meta != null) {
            ResolvedBrand(
                answer = meta.answer,
                hint = meta.hint,
                trivia = meta.trivia,
                logoKey = clean
            )
        } else {
            val uppercaseAnswer = clean.uppercase().take(12)
            ResolvedBrand(
                answer = if (uppercaseAnswer.isNotBlank()) uppercaseAnswer else "BRAND",
                hint = "Iconic worldwide brand recognized by its distinctive emblem",
                trivia = "A world-renowned brand mark celebrated in its industry with millions of daily users.",
                logoKey = clean
            )
        }
    }
}
