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
    val gradientColorsHex: List<Long>
) {
    AUTOMOTIVE(
        id = "automotive",
        title = "Automotive",
        subtitle = "Car brands & motorcycles",
        folderName = "Automotive",
        emoji = "🚗",
        primaryColorHex = 0xFFD97706,
        dimColorHex = 0xFFFDF6EE,
        gradientColorsHex = listOf(0xFFB45309, 0xFFF59E0B)
    ),
    FOOD_BEVERAGE(
        id = "food_beverage",
        title = "Food & Beverage",
        subtitle = "Fast food, drinks & snacks",
        folderName = "Food & Beverage",
        emoji = "🍔",
        primaryColorHex = 0xFFE11D48,
        dimColorHex = 0xFFFFF1F2,
        gradientColorsHex = listOf(0xFFBE123C, 0xFFFB7185)
    ),
    TECHNOLOGY(
        id = "technology",
        title = "Technology",
        subtitle = "Software, apps & hardware",
        folderName = "Technology",
        emoji = "💻",
        primaryColorHex = 0xFF0D9488,
        dimColorHex = 0xFFF0FDFA,
        gradientColorsHex = listOf(0xFF0F766E, 0xFF14B8A6)
    ),
    FASHION_CLOTHING(
        id = "fashion_clothing",
        title = "Fashion & Clothing",
        subtitle = "Sportswear & luxury fashion",
        folderName = "Fashion & Clothing",
        emoji = "👕",
        primaryColorHex = 0xFF7C3AED,
        dimColorHex = 0xFFF5F3FF,
        gradientColorsHex = listOf(0xFF6D28D9, 0xFFA78BFA)
    ),
    ENTERTAINMENT(
        id = "entertainment",
        title = "Entertainment",
        subtitle = "Streaming, gaming & movies",
        folderName = "Entertainment",
        emoji = "📺",
        primaryColorHex = 0xFFEA580C,
        dimColorHex = 0xFFFFF7ED,
        gradientColorsHex = listOf(0xFFC2410C, 0xFFFB923C)
    ),
    FINANCE_BANKING(
        id = "finance_banking",
        title = "Finance & Banking",
        subtitle = "Banks, cards & payments",
        folderName = "Finance & Banking",
        emoji = "🏦",
        primaryColorHex = 0xFF2563EB,
        dimColorHex = 0xFFEFF6FF,
        gradientColorsHex = listOf(0xFF1D4ED8, 0xFF60A5FA)
    ),
    TRAVEL_AIRLINES(
        id = "travel_airlines",
        title = "Travel & Airlines",
        subtitle = "Airlines, hotels & booking",
        folderName = "Travel & Airlines",
        emoji = "✈️",
        primaryColorHex = 0xFF0284C7,
        dimColorHex = 0xFFF0F9FF,
        gradientColorsHex = listOf(0xFF0369A1, 0xFF38BDF8)
    ),
    SPORTS(
        id = "sports",
        title = "Sports",
        subtitle = "Teams, leagues & gear",
        folderName = "Sports",
        emoji = "⚽",
        primaryColorHex = 0xFF65A30D,
        dimColorHex = 0xFFF7FEE7,
        gradientColorsHex = listOf(0xFF4D7C0F, 0xFFA3E635)
    ),
    BEAUTY_PERSONAL_CARE(
        id = "beauty_personal_care",
        title = "Beauty & Personal Care",
        subtitle = "Cosmetics, skincare & perfume",
        folderName = "Beauty & Personal Care",
        emoji = "🧴",
        primaryColorHex = 0xFFDB2777,
        dimColorHex = 0xFFFDF2F8,
        gradientColorsHex = listOf(0xFFBE185D, 0xFFF472B6)
    ),
    RETAIL_SUPERMARKETS(
        id = "retail_supermarkets",
        title = "Retail & Supermarkets",
        subtitle = "Stores & global e-commerce",
        folderName = "Retail & Supermarkets",
        emoji = "🏪",
        primaryColorHex = 0xFFCA8A04,
        dimColorHex = 0xFFFEFCE8,
        gradientColorsHex = listOf(0xFFA16207, 0xFFFACC15)
    ),
    SOCIAL_MEDIA(
        id = "social_media",
        title = "Social Media",
        subtitle = "Social platforms & video",
        folderName = "Social Media",
        emoji = "📱",
        primaryColorHex = 0xFF4F46E5,
        dimColorHex = 0xFFEEF2FF,
        gradientColorsHex = listOf(0xFF4338CA, 0xFF818CF8)
    ),
    HEALTH_PHARMA(
        id = "health_pharma",
        title = "Health & Pharma",
        subtitle = "Pharma & health giants",
        folderName = "Health & Pharma",
        emoji = "🏥",
        primaryColorHex = 0xFF059669,
        dimColorHex = 0xFFECFDF5,
        gradientColorsHex = listOf(0xFF047857, 0xFF34D399)
    ),
    ENERGY_TELECOM(
        id = "energy_telecom",
        title = "Energy & Telecom",
        subtitle = "Energy & telecom networks",
        folderName = "Energy & Telecom",
        emoji = "⚡",
        primaryColorHex = 0xFF0891B2,
        dimColorHex = 0xFFECFEFF,
        gradientColorsHex = listOf(0xFF0E7490, 0xFF22D3EE)
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
