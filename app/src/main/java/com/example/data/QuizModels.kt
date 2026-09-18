package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class PackCategory(
    val id: String,
    val title: String,
    val subtitle: String,
    val folderName: String,
    val emoji: String,
    val primaryColorHex: Long,
    val dimColorHex: Long,
    val gradientColorsHex: List<Long>,
    val accentColorHex: Long = primaryColorHex
) {
    AUTOMOTIVE(
        id = "automotive",
        title = "Automotive",
        subtitle = "Car brands & motorcycles",
        folderName = "Automotive",
        emoji = "🏎️",
        primaryColorHex = 0xFFFF6B35,
        dimColorHex = 0xFF1C1C28,
        gradientColorsHex = listOf(0xFFFF6B35, 0xFFFFB347),
        accentColorHex = 0xFFFF6B35
    ),
    FOOD_BEVERAGE(
        id = "food_beverage",
        title = "Food & Drink",
        subtitle = "Fast food, drinks & snacks",
        folderName = "Food & Beverages",
        emoji = "🍔",
        primaryColorHex = 0xFF00F5A0,
        dimColorHex = 0xFF182522,
        gradientColorsHex = listOf(0xFF00F5A0, 0xFF00D9FF),
        accentColorHex = 0xFF00F5A0
    ),
    TECHNOLOGY(
        id = "technology",
        title = "Tech Giants",
        subtitle = "Software, apps & hardware",
        folderName = "Technology",
        emoji = "💻",
        primaryColorHex = 0xFF7C3AED,
        dimColorHex = 0xFF221825,
        gradientColorsHex = listOf(0xFF7C3AED, 0xFFEC4899),
        accentColorHex = 0xFF8B5CF6
    ),
    FASHION_CLOTHING(
        id = "fashion_clothing",
        title = "Fashion",
        subtitle = "Sportswear & luxury fashion",
        folderName = "Fashion & Clothing",
        emoji = "👟",
        primaryColorHex = 0xFFFF3B8E,
        dimColorHex = 0xFF281820,
        gradientColorsHex = listOf(0xFFFF3B8E, 0xFFFF8E53),
        accentColorHex = 0xFFFF3B8E
    ),
    ENTERTAINMENT(
        id = "entertainment",
        title = "Movies & Media",
        subtitle = "Streaming, gaming & movies",
        folderName = "Entertainment",
        emoji = "🎬",
        primaryColorHex = 0xFF22D3EE,
        dimColorHex = 0xFF14242A,
        gradientColorsHex = listOf(0xFF22D3EE, 0xFF3B82F6),
        accentColorHex = 0xFF22D3EE
    ),
    FINANCE_BANKING(
        id = "finance_banking",
        title = "Finance",
        subtitle = "Banks, cards & payments",
        folderName = "Finance & Banking",
        emoji = "💳",
        primaryColorHex = 0xFF3B82F6,
        dimColorHex = 0xFF161E2C,
        gradientColorsHex = listOf(0xFF3B82F6, 0xFF60A5FA),
        accentColorHex = 0xFF3B82F6
    ),
    SPORTS(
        id = "sports",
        title = "Sports",
        subtitle = "Teams, leagues & gear",
        folderName = "Sports",
        emoji = "⚽",
        primaryColorHex = 0xFFFACC15,
        dimColorHex = 0xFF282414,
        gradientColorsHex = listOf(0xFFFACC15, 0xFFFB923C),
        accentColorHex = 0xFFFACC15
    ),
    RETAIL_SUPERMARKETS(
        id = "retail_supermarkets",
        title = "Retail",
        subtitle = "Stores & global e-commerce",
        folderName = "Retail & Supermarkets",
        emoji = "🛒",
        primaryColorHex = 0xFFF59E0B,
        dimColorHex = 0xFF262014,
        gradientColorsHex = listOf(0xFFF59E0B, 0xFFFBBF24),
        accentColorHex = 0xFFF59E0B
    )
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
    val imageUrl: String? = null,
    val originalName: String = answer,
    val alternateAnswers: List<String> = emptyList()
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
