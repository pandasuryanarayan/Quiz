package com.example

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.data.PackCategory
import com.example.data.QuizDatabase
import com.example.data.QuizRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Logo Quiz", appName)
  }

  @Test
  fun `verify quiz packs catalog and CDN mappings`() {
    val levels = com.example.data.QuizPackData.allLevels
    assertEquals(48, levels.size)

    // Verify Famous Brands sequence
    val brands = com.example.data.QuizPackData.getLevelsForPack("brands")
    assertEquals(9, brands.size)
    assertEquals("AMAZON", brands[0].answer)
    assertEquals("APPLE", brands[1].answer)
    assertEquals("GOOGLE", brands[2].answer)
    assertEquals("MCDONALDS", brands[3].answer)
    assertEquals("NIKE", brands[4].answer)
    assertEquals("SPOTIFY", brands[5].answer)
    assertEquals("TARGET", brands[6].answer)
    assertEquals("TESLA", brands[7].answer)
    assertEquals("ZOHO", brands[8].answer)
    brands.forEach { lvl ->
      org.junit.Assert.assertNotNull(lvl.imageUrl)
      org.junit.Assert.assertTrue(lvl.imageUrl!!.startsWith("https://cdn.jsdelivr.net/gh/pandasuryanarayan/logoquiz/Famous%20Brands/"))
    }

    // Verify Entertainment sequence
    val entertainment = com.example.data.QuizPackData.getLevelsForPack("entertainment")
    assertEquals(5, entertainment.size)
    assertEquals("DISNEY", entertainment[0].answer)
    assertEquals("NETFLIX", entertainment[1].answer)
    assertEquals("TWITCH", entertainment[2].answer)
    assertEquals("WARNERBROS", entertainment[3].answer)
    assertEquals("YOUTUBE", entertainment[4].answer)
    entertainment.forEach { lvl ->
      org.junit.Assert.assertNotNull(lvl.imageUrl)
      org.junit.Assert.assertTrue(lvl.imageUrl!!.startsWith("https://cdn.jsdelivr.net/gh/pandasuryanarayan/logoquiz/Entertainment/"))
    }

    // Verify Food sequence
    val food = com.example.data.QuizPackData.getLevelsForPack("food")
    assertEquals(5, food.size)
    assertEquals("BURGERKING", food[0].answer)
    assertEquals("DOMINOS", food[1].answer)
    assertEquals("KFC", food[2].answer)
    assertEquals("PEPSI", food[3].answer)
    assertEquals("TACOBELL", food[4].answer)
    food.forEach { lvl ->
      org.junit.Assert.assertNotNull(lvl.imageUrl)
      org.junit.Assert.assertTrue(lvl.imageUrl!!.startsWith("https://cdn.jsdelivr.net/gh/pandasuryanarayan/logoquiz/Food%20%26%20Treats/"))
    }

    // Verify Sports sequence
    val sports = com.example.data.QuizPackData.getLevelsForPack("sports")
    assertEquals(9, sports.size)
    assertEquals("ADIDAS", sports[0].answer)
    assertEquals("AUDI", sports[1].answer)
    assertEquals("BMW", sports[2].answer)
    assertEquals("FERRARI", sports[3].answer)
    assertEquals("MERCEDES", sports[4].answer)
    assertEquals("NBA", sports[5].answer)
    assertEquals("OLYMPIC", sports[6].answer)
    assertEquals("PUMA", sports[7].answer)
    assertEquals("REDBULL", sports[8].answer)
    sports.forEach { lvl ->
      org.junit.Assert.assertNotNull(lvl.imageUrl)
      org.junit.Assert.assertTrue(lvl.imageUrl!!.startsWith("https://cdn.jsdelivr.net/gh/pandasuryanarayan/logoquiz/Sports%20%26%20Autos/"))
    }
  }

  @Test
  fun `verify watching ad increases coins`() = runBlocking {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val db = Room.inMemoryDatabaseBuilder(context, QuizDatabase::class.java).allowMainThreadQueries().build()
    val repo = QuizRepository(db.quizDao())
    repo.initializeDefaultsIfNeeded()

    val profileBefore = db.quizDao().getUserProfileSync()
    assertEquals(150, profileBefore?.coins)
    assertEquals(0, profileBefore?.adsWatched)

    // Simulate watching rewarded ad (+50 coins)
    repo.rewardAdWatch(50)

    val profileAfter = db.quizDao().getUserProfileSync()
    assertEquals(200, profileAfter?.coins)
    assertEquals(1, profileAfter?.adsWatched)

    db.close()
  }

  @Test
  fun `verify sequential level unlock progression`() = runBlocking {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val db = Room.inMemoryDatabaseBuilder(context, QuizDatabase::class.java).allowMainThreadQueries().build()
    val repo = QuizRepository(db.quizDao())
    repo.initializeDefaultsIfNeeded()

    val level1 = com.example.data.QuizPackData.getLevelById("brands_1")!!
    val level2 = com.example.data.QuizPackData.getLevelById("brands_2")!!
    val level5 = com.example.data.QuizPackData.getLevelById("brands_5")!!
    val level6 = com.example.data.QuizPackData.getLevelById("brands_6")!!
    val level7 = com.example.data.QuizPackData.getLevelById("brands_7")!!

    var allProgress = db.quizDao().getAllLevelProgress()
    // Helper to get sync list
    fun getProgressList() = runBlocking {
      com.example.data.QuizPackData.allLevels.mapNotNull { lvl ->
        db.quizDao().getLevelProgressSync(lvl.id)
      }
    }

    var progressList = getProgressList()

    // 1. Level 1 must be unlocked initially
    val statusL1 = com.example.data.QuizPackData.getLevelLockStatus(level1, progressList)
    assertEquals(true, statusL1.isUnlocked)
    assertEquals(false, statusL1.isStrictlyLocked)

    // 2. Level 2 must be strictly locked initially because Level 1 is not completed
    val statusL2 = com.example.data.QuizPackData.getLevelLockStatus(level2, progressList)
    assertEquals(false, statusL2.isUnlocked)
    assertEquals(true, statusL2.isStrictlyLocked)
    assertEquals("brands_1", statusL2.requiredPreviousLevel?.id)

    // 3. Level 6 (ad-gated) must be strictly locked because Level 5 is not completed
    val statusL6Before = com.example.data.QuizPackData.getLevelLockStatus(level6, progressList)
    assertEquals(false, statusL6Before.isUnlocked)
    assertEquals(false, statusL6Before.isAdGated)
    assertEquals(true, statusL6Before.isStrictlyLocked)

    // 4. Complete Level 1 -> Level 2 should now be unlocked
    repo.completeLevel(level1.id)
    progressList = getProgressList()

    val statusL2After = com.example.data.QuizPackData.getLevelLockStatus(level2, progressList)
    assertEquals(true, statusL2After.isUnlocked)
    assertEquals(false, statusL2After.isStrictlyLocked)

    // 5. Complete levels 2, 3, 4, 5
    repo.completeLevel("brands_2")
    repo.completeLevel("brands_3")
    repo.completeLevel("brands_4")
    repo.completeLevel("brands_5")
    progressList = getProgressList()

    // Now Level 6 should be eligible for Ad-Unlock (isAdGated = true, but not unlocked yet)
    val statusL6Eligible = com.example.data.QuizPackData.getLevelLockStatus(level6, progressList)
    assertEquals(false, statusL6Eligible.isUnlocked)
    assertEquals(true, statusL6Eligible.isAdGated)
    assertEquals(false, statusL6Eligible.isStrictlyLocked)

    // And Level 7 should still be strictly locked because Level 6 is not completed
    val statusL7Locked = com.example.data.QuizPackData.getLevelLockStatus(level7, progressList)
    assertEquals(false, statusL7Locked.isUnlocked)
    assertEquals(false, statusL7Locked.isAdGated)
    assertEquals(true, statusL7Locked.isStrictlyLocked)

    // 6. Unlock Level 6 via ad
    repo.unlockLevel(level6.id)
    progressList = getProgressList()

    val statusL6Unlocked = com.example.data.QuizPackData.getLevelLockStatus(level6, progressList)
    assertEquals(true, statusL6Unlocked.isUnlocked)
    assertEquals(false, statusL6Unlocked.isAdGated)

    // 7. Complete Level 6 -> Level 7 becomes eligible for ad unlock
    repo.completeLevel(level6.id)
    progressList = getProgressList()

    val statusL7Eligible = com.example.data.QuizPackData.getLevelLockStatus(level7, progressList)
    assertEquals(false, statusL7Eligible.isUnlocked)
    assertEquals(true, statusL7Eligible.isAdGated)
    assertEquals(false, statusL7Eligible.isStrictlyLocked)

    db.close()
  }

  @Test
  fun `verify only level 1 is shown until passed, then level 2 shown`() = runBlocking {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val db = Room.inMemoryDatabaseBuilder(context, QuizDatabase::class.java).allowMainThreadQueries().build()
    val repo = QuizRepository(db.quizDao())
    repo.initializeDefaultsIfNeeded()

    // 1. Initially, only Level 1 must be visible in the pack
    var visible = com.example.data.QuizPackData.getVisibleLevelsForPack("brands", repo.allProgress.value)
    assertEquals(1, visible.size)
    assertEquals("brands_1", visible[0].id)

    // 2. User completes Level 1 -> now Level 1 and Level 2 are visible
    repo.completeLevel("brands_1")
    visible = com.example.data.QuizPackData.getVisibleLevelsForPack("brands", repo.allProgress.value)
    assertEquals(2, visible.size)
    assertEquals("brands_1", visible[0].id)
    assertEquals("brands_2", visible[1].id)

    // 3. User completes Level 2 -> now Level 1, 2, and 3 are visible
    repo.completeLevel("brands_2")
    visible = com.example.data.QuizPackData.getVisibleLevelsForPack("brands", repo.allProgress.value)
    assertEquals(3, visible.size)
    assertEquals("brands_3", visible[2].id)

    db.close()
  }

  @Test
  fun `verify all categories have levels seeded properly and level 1 visible`() = runBlocking {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val db = Room.inMemoryDatabaseBuilder(context, QuizDatabase::class.java).allowMainThreadQueries().build()
    val repo = QuizRepository(db.quizDao())
    repo.initializeDefaultsIfNeeded()

    assertEquals(6, PackCategory.entries.size)

    val expectedCounts = mapOf(
      "brands" to 9,
      "entertainment" to 5,
      "gaming" to 10,
      "sports" to 9,
      "food" to 5,
      "world" to 10
    )

    for (pack in PackCategory.entries) {
      val packLevels = com.example.data.QuizPackData.getLevelsForPack(pack.id)
      assertEquals("Pack ${pack.id} size mismatch", expectedCounts[pack.id], packLevels.size)
      // Level 1 should be free & visible initially
      val visible = com.example.data.QuizPackData.getVisibleLevelsForPack(pack.id, repo.allProgress.value)
      assertEquals("Pack ${pack.id} should show only Level 1 initially", 1, visible.size)
      assertEquals("${pack.id}_1", visible[0].id)
    }

    db.close()
  }
}
