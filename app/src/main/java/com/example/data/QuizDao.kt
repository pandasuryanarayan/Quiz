package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizDao {

    @Query("SELECT * FROM level_progress")
    fun getAllLevelProgress(): Flow<List<LevelProgressEntity>>

    @Query("SELECT * FROM level_progress")
    suspend fun getAllLevelProgressSync(): List<LevelProgressEntity>

    @Query("SELECT * FROM level_progress WHERE packId = :packId ORDER BY levelNumber ASC")
    fun getProgressForPack(packId: String): Flow<List<LevelProgressEntity>>

    @Query("SELECT * FROM level_progress WHERE id = :levelId LIMIT 1")
    fun getLevelProgress(levelId: String): Flow<LevelProgressEntity?>

    @Query("SELECT * FROM level_progress WHERE id = :levelId LIMIT 1")
    suspend fun getLevelProgressSync(levelId: String): LevelProgressEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertInitialProgress(progressList: List<LevelProgressEntity>)

    @Update
    suspend fun updateProgress(progress: LevelProgressEntity)

    @Query("UPDATE level_progress SET isUnlocked = 1 WHERE id = :levelId")
    suspend fun unlockLevel(levelId: String)

    @Query("UPDATE level_progress SET isUnlocked = 0 WHERE id = :levelId")
    suspend fun lockLevel(levelId: String)

    @Query("UPDATE level_progress SET isCompleted = 1, stars = :stars, solvedTimestamp = :timestamp WHERE id = :levelId")
    suspend fun markLevelCompleted(levelId: String, stars: Int, timestamp: Long)

    @Query("DELETE FROM level_progress WHERE id = :levelId")
    suspend fun deleteLevelProgress(levelId: String)

    @Query("SELECT * FROM user_profile WHERE id = 1 LIMIT 1")
    fun getUserProfile(): Flow<UserProfileEntity?>

    @Query("SELECT * FROM user_profile WHERE id = 1 LIMIT 1")
    suspend fun getUserProfileSync(): UserProfileEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertInitialProfile(profile: UserProfileEntity)

    @Query("UPDATE user_profile SET coins = coins + :coinsDelta, totalXp = totalXp + :xpDelta, levelsSolved = levelsSolved + :levelsDelta WHERE id = 1")
    suspend fun addRewards(coinsDelta: Int, xpDelta: Int, levelsDelta: Int = 0)

    @Query("UPDATE user_profile SET coins = coins - :amount WHERE id = 1 AND coins >= :amount")
    suspend fun deductCoins(amount: Int): Int

    @Query("UPDATE user_profile SET adsWatched = adsWatched + 1 WHERE id = 1")
    suspend fun incrementAdsWatched()

    @Query("UPDATE level_progress SET isCompleted = 0, stars = 0, hintsUsed = 0, solvedTimestamp = 0, isUnlocked = CASE WHEN levelNumber = 1 THEN 1 ELSE 0 END")
    suspend fun resetAllLevelProgress()

    @Query("UPDATE user_profile SET coins = :defaultCoins, totalXp = 0, levelsSolved = 0, adsWatched = 0 WHERE id = 1")
    suspend fun resetUserProfile(defaultCoins: Int = 150)
}
