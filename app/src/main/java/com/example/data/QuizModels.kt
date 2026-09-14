package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class PackCategory(
    val id: String,
    val title: String,
    val subtitle: String,
    val iconKey: String,
    val emoji: String,
    val primaryColorHex: Long,
    val dimColorHex: Long
) {
    FOOD("food", "Foods", "Snacks, drinks & food chains", "restaurant", "🥐", 0xFFD97706, 0xFFFDF4E7),
    GAMING("gaming", "Games", "Gaming consoles & studios", "gamepad", "🎮", 0xFF0D9488, 0xFFE6F5F4),
    ENTERTAINMENT("entertainment", "Entertainment", "Movies, TV & streaming", "movie", "🎬", 0xFFBE123C, 0xFFFDF0F3),
    BRANDS("brands", "Famous Brands", "Global brands & trademarks", "tag", "💼", 0xFF2563EB, 0xFFEFF4FE),
    SPORTS("sports", "Sports", "Athletic gear & leagues", "trophy", "⚽", 0xFF65A30D, 0xFFF4F9E9),
    WORLD("world", "Tech", "Apps, gadgets & tech titans", "globe", "📱", 0xFF9333EA, 0xFFF8EFFF)
}

enum class AppMode {
    USER,
    ADMIN
}

data class QuizLevel(
    val id: String,
    val packId: String,
    val levelNumber: Int,
    val answer: String, // Clean uppercase alphabetical (e.g. "APPLE")
    val hintSentence: String,
    val triviaFact: String,
    val logoKey: String,
    val imageUrl: String? = null
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
