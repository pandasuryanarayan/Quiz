package com.example.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class QuizRepository(private val quizDao: QuizDao) {

    val allProgress: Flow<List<LevelProgressEntity>> = quizDao.getAllLevelProgress()
    val userProfile: Flow<UserProfileEntity?> = quizDao.getUserProfile()

    suspend fun initializeDefaultsIfNeeded() = withContext(Dispatchers.IO) {
        // Initialize user profile if not exists
        val existingProfile = quizDao.getUserProfileSync()
        if (existingProfile == null) {
            quizDao.insertInitialProfile(UserProfileEntity(id = 1, coins = 150, totalXp = 0))
        }

        // Initialize level progresses
        val initialEntities = QuizPackData.allLevels.map { level ->
            LevelProgressEntity(
                id = level.id,
                packId = level.packId,
                levelNumber = level.levelNumber,
                isUnlocked = level.levelNumber <= 5, // Stages 1..5 unlocked by default
                isCompleted = false,
                stars = 0,
                hintsUsed = 0
            )
        }
        quizDao.insertInitialProgress(initialEntities)
    }

    fun getProgressForPack(packId: String): Flow<List<LevelProgressEntity>> {
        return quizDao.getProgressForPack(packId)
    }

    fun getLevelProgress(levelId: String): Flow<LevelProgressEntity?> {
        return quizDao.getLevelProgress(levelId)
    }

    suspend fun unlockLevel(levelId: String) = withContext(Dispatchers.IO) {
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
    }

    suspend fun spendCoins(amount: Int): Boolean = withContext(Dispatchers.IO) {
        val rows = quizDao.deductCoins(amount)
        rows > 0
    }

    suspend fun rewardAdWatch(bonusCoins: Int = 25) = withContext(Dispatchers.IO) {
        quizDao.addRewards(coinsDelta = bonusCoins, xpDelta = 10, levelsDelta = 0)
        quizDao.incrementAdsWatched()
    }
}
