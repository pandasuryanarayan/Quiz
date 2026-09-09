package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class PackCategory(
    val id: String,
    val title: String,
    val subtitle: String,
    val iconKey: String,
    val primaryColorHex: Long
) {
    BRANDS("brands", "Famous Brands", "Iconic global logos & company trademarks", "tag", 0xFF3B82F6),
    ENTERTAINMENT("entertainment", "Entertainment", "Movies, TV, studios & streaming giants", "movie", 0xFF8B5CF6),
    GAMING("gaming", "Gaming & Tech", "Consoles, studios, retro games & tech titans", "gamepad", 0xFF10B981),
    SPORTS("sports", "Sports & Autos", "Supercars, athletic gear & sports leagues", "trophy", 0xFFF59E0B)
}

data class QuizLevel(
    val id: String,
    val packId: String,
    val levelNumber: Int,
    val answer: String, // Clean uppercase alphabetical (e.g. "APPLE")
    val hintSentence: String,
    val triviaFact: String,
    val logoKey: String
)

@Entity(tableName = "level_progress")
data class LevelProgressEntity(
    @PrimaryKey val id: String,
    val packId: String,
    val levelNumber: Int,
    val isUnlocked: Boolean = false,
    val isCompleted: Boolean = false,
    val stars: Int = 0,
    val hintsUsed: Int = 0,
    val solvedTimestamp: Long = 0L
)

@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey val id: Int = 1,
    val coins: Int = 150,
    val totalXp: Int = 0,
    val levelsSolved: Int = 0,
    val adsWatched: Int = 0
)

data class PackProgressSummary(
    val pack: PackCategory,
    val totalLevels: Int,
    val unlockedLevels: Int,
    val completedLevels: Int,
    val totalStars: Int
)
