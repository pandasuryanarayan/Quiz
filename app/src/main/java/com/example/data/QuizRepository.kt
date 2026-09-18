package com.example.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class QuizRepository(private val quizDao: QuizDao) {

    private val _allProgress = MutableStateFlow<List<LevelProgressEntity>>(emptyList())
    val allProgress: StateFlow<List<LevelProgressEntity>> = _allProgress.asStateFlow()

    val userProfile: Flow<UserProfileEntity?> = quizDao.getUserProfile()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            quizDao.getAllLevelProgress().collect { dbList ->
                if (dbList.isNotEmpty()) {
                    _allProgress.value = dbList
                }
            }
        }
    }

    suspend fun initializeDefaultsIfNeeded() = withContext(Dispatchers.IO) {
        // Initialize user profile if not exists
        val existingProfile = quizDao.getUserProfileSync()
        if (existingProfile == null) {
            quizDao.insertInitialProfile(UserProfileEntity(id = 1, coins = 150, totalXp = 0))
        }

        // Initialize level progresses (Level 1 of each pack unlocked initially)
        val existingProgress = quizDao.getAllLevelProgressSync()
        if (existingProgress.isEmpty()) {
            val initialEntities = QuizPackData.allLevels.map { level ->
                LevelProgressEntity(
                    id = level.id,
                    packId = level.packId,
                    levelNumber = level.levelNumber,
                    isUnlocked = level.levelNumber == 1, // Only Level 1 of each pack is unlocked initially
                    isCompleted = false,
                    stars = 0,
                    hintsUsed = 0
                )
            }
            quizDao.insertInitialProgress(initialEntities)
            _allProgress.value = initialEntities
        } else {
            // Remove obsolete level progress entries that no longer exist in QuizPackData
            val currentValidIds = QuizPackData.allLevels.map { it.id }.toSet()
            val obsolete = existingProgress.filter { it.id !in currentValidIds }
            for (obs in obsolete) {
                quizDao.deleteLevelProgress(obs.id)
            }
            val validExisting = existingProgress.filter { it.id in currentValidIds }

            // Check if any new levels or categories were added to QuizPackData that are missing in DB
            val existingIds = validExisting.map { it.id }.toSet()
            val missingLevels = QuizPackData.allLevels.filter { it.id !in existingIds }
            val newlyAddedEntities = if (missingLevels.isNotEmpty()) {
                val newEntities = missingLevels.map { level ->
                    LevelProgressEntity(
                        id = level.id,
                        packId = level.packId,
                        levelNumber = level.levelNumber,
                        isUnlocked = level.levelNumber == 1,
                        isCompleted = false,
                        stars = 0,
                        hintsUsed = 0
                    )
                }
                quizDao.insertInitialProgress(newEntities)
                newEntities
            } else {
                emptyList()
            }

            val combined = validExisting + newlyAddedEntities

            // Sanitize legacy or dirty database entries from earlier app versions:
            // Any level N > 1 where level N - 1 is NOT completed must be locked!
            val sanitized = combined.map { entity ->
                if (entity.levelNumber > 1 && !entity.isCompleted) {
                    val prev = combined.find { it.packId == entity.packId && it.levelNumber == entity.levelNumber - 1 }
                    if (prev?.isCompleted != true && entity.isUnlocked) {
                        quizDao.lockLevel(entity.id)
                        entity.copy(isUnlocked = false)
                    } else {
                        entity
                    }
                } else {
                    entity
                }
            }
            _allProgress.value = sanitized
        }
    }

    fun getProgressForPack(packId: String): Flow<List<LevelProgressEntity>> {
        return quizDao.getProgressForPack(packId)
    }

    fun getLevelProgress(levelId: String): Flow<LevelProgressEntity?> {
        return quizDao.getLevelProgress(levelId)
    }

    suspend fun unlockLevel(levelId: String) = withContext(Dispatchers.IO) {
        // Synchronously update in-memory state
        val updated = _allProgress.value.map { entity ->
            if (entity.id == levelId) entity.copy(isUnlocked = true) else entity
        }
        _allProgress.value = updated

        quizDao.unlockLevel(levelId)
        quizDao.incrementAdsWatched()
    }

    suspend fun completeLevel(levelId: String, stars: Int = 3, coinsAwarded: Int = 50, xpAwarded: Int = 100) = withContext(Dispatchers.IO) {
        val existing = quizDao.getLevelProgressSync(levelId)
        val wasCompleted = existing?.isCompleted == true
        quizDao.markLevelCompleted(levelId, stars, System.currentTimeMillis())
        if (!wasCompleted) {
            quizDao.addRewards(coinsDelta = coinsAwarded, xpDelta = xpAwarded, levelsDelta = 1)
        }

        // Sequential progression: when level N is completed, unlock level N+1 if it's a free level (<= 5)
        val currentLevel = QuizPackData.getLevelById(levelId)
        val nextLevelId = if (currentLevel != null) {
            val nextLevelNumber = currentLevel.levelNumber + 1
            val nextLevel = QuizPackData.getLevelsForPack(currentLevel.packId).find { it.levelNumber == nextLevelNumber }
            if (nextLevel != null && nextLevel.levelNumber <= 5) {
                quizDao.unlockLevel(nextLevel.id)
                nextLevel.id
            } else {
                null
            }
        } else null

        // Immediately update in-memory state so UI and next-level flows react with zero lag
        val updated = _allProgress.value.map { entity ->
            when (entity.id) {
                levelId -> entity.copy(isCompleted = true, stars = stars)
                nextLevelId -> entity.copy(isUnlocked = true)
                else -> entity
            }
        }
        _allProgress.value = updated
    }

    suspend fun spendCoins(amount: Int): Boolean = withContext(Dispatchers.IO) {
        val rows = quizDao.deductCoins(amount)
        rows > 0
    }

    suspend fun rewardAdWatch(bonusCoins: Int = 25) = withContext(Dispatchers.IO) {
        quizDao.addRewards(coinsDelta = bonusCoins, xpDelta = 10, levelsDelta = 0)
        quizDao.incrementAdsWatched()
    }

    suspend fun loadCachedRemoteLevels(context: android.content.Context) = withContext(Dispatchers.IO) {
        val cached = RemoteLogoSyncManager.loadCachedLevels(context)
        if (cached.isNotEmpty()) {
            QuizPackData.addLevels(cached)
            reconcileMissingLevelsInDb()
        }
    }

    suspend fun syncRemoteLogos(context: android.content.Context, targetPackId: String? = null): RemoteLogoSyncManager.SyncResult {
        val result = RemoteLogoSyncManager.syncRemoteLogos(context, targetPackId)
        if (result.newLevelsCount > 0) {
            reconcileMissingLevelsInDb()
        }
        return result
    }

    suspend fun resetPlayerProgress() = withContext(Dispatchers.IO) {
        quizDao.resetAllLevelProgress()
        quizDao.resetUserProfile(defaultCoins = 150)
        // Purge any obsolete or duplicated entries from the database
        val currentValidIds = QuizPackData.allLevels.map { it.id }.toSet()
        val allInDb = quizDao.getAllLevelProgressSync()
        val obsolete = allInDb.filter { it.id !in currentValidIds }
        for (obs in obsolete) {
            quizDao.deleteLevelProgress(obs.id)
        }
        val refreshed = quizDao.getAllLevelProgressSync()
        _allProgress.value = refreshed
    }

    private suspend fun reconcileMissingLevelsInDb() = withContext(Dispatchers.IO) {
        val existingProgress = quizDao.getAllLevelProgressSync()
        val existingIds = existingProgress.map { it.id }.toSet()
        val missingLevels = QuizPackData.allLevels.filter { it.id !in existingIds }
        if (missingLevels.isNotEmpty()) {
            val newEntities = missingLevels.map { level ->
                LevelProgressEntity(
                    id = level.id,
                    packId = level.packId,
                    levelNumber = level.levelNumber,
                    isUnlocked = level.levelNumber == 1,
                    isCompleted = false,
                    stars = 0,
                    hintsUsed = 0
                )
            }
            quizDao.insertInitialProgress(newEntities)
            _allProgress.value = existingProgress + newEntities
        }
    }
}
