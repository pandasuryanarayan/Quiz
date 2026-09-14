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
    assertEquals(130, levels.size)

    // Verify Automotive sequence
    val automotive = com.example.data.QuizPackData.getLevelsForPack("automotive")
    assertEquals(10, automotive.size)
    assertEquals("TOYOTA", automotive[0].answer)
    assertEquals("BMW", automotive[1].answer)
    assertEquals("FERRARI", automotive[2].answer)
    automotive.forEach { lvl ->
      org.junit.Assert.assertNotNull(lvl.imageUrl)
      org.junit.Assert.assertTrue(lvl.imageUrl!!.startsWith("https://cdn.jsdelivr.net/gh/pandasuryanarayan/logoquiz/Automotive/"))
    }

    // Verify Food & Beverage sequence
    val food = com.example.data.QuizPackData.getLevelsForPack("food_beverage")
    assertEquals(10, food.size)
    assertEquals("MCDONALDS", food[0].answer)
    assertEquals("KFC", food[1].answer)
    assertEquals("COCACOLA", food[2].answer)
    food.forEach { lvl ->
      org.junit.Assert.assertNotNull(lvl.imageUrl)
      org.junit.Assert.assertTrue(lvl.imageUrl!!.startsWith("https://cdn.jsdelivr.net/gh/pandasuryanarayan/logoquiz/Food%20%26%20Beverage/"))
    }

    // Verify Energy & Telecom sequence
    val energy = com.example.data.QuizPackData.getLevelsForPack("energy_telecom")
    assertEquals(10, energy.size)
    assertEquals("SHELL", energy[0].answer)
    assertEquals("VODAFONE", energy[1].answer)
    assertEquals("VERIZON", energy[2].answer)
    energy.forEach { lvl ->
      org.junit.Assert.assertNotNull(lvl.imageUrl)
      org.junit.Assert.assertTrue(lvl.imageUrl!!.startsWith("https://cdn.jsdelivr.net/gh/pandasuryanarayan/logoquiz/Energy%20%26%20Telecom/"))
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

    val level1 = com.example.data.QuizPackData.getLevelById("automotive_1")!!
    val level2 = com.example.data.QuizPackData.getLevelById("automotive_2")!!
    val level5 = com.example.data.QuizPackData.getLevelById("automotive_5")!!
    val level6 = com.example.data.QuizPackData.getLevelById("automotive_6")!!
    val level7 = com.example.data.QuizPackData.getLevelById("automotive_7")!!

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
    assertEquals("automotive_1", statusL2.requiredPreviousLevel?.id)

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
    repo.completeLevel("automotive_2")
    repo.completeLevel("automotive_3")
    repo.completeLevel("automotive_4")
    repo.completeLevel("automotive_5")
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
    var visible = com.example.data.QuizPackData.getVisibleLevelsForPack("automotive", repo.allProgress.value)
    assertEquals(1, visible.size)
    assertEquals("automotive_1", visible[0].id)

    // 2. User completes Level 1 -> now Level 1 and Level 2 are visible
    repo.completeLevel("automotive_1")
    visible = com.example.data.QuizPackData.getVisibleLevelsForPack("automotive", repo.allProgress.value)
    assertEquals(2, visible.size)
    assertEquals("automotive_1", visible[0].id)
    assertEquals("automotive_2", visible[1].id)

    // 3. User completes Level 2 -> now Level 1, 2, and 3 are visible
    repo.completeLevel("automotive_2")
    visible = com.example.data.QuizPackData.getVisibleLevelsForPack("automotive", repo.allProgress.value)
    assertEquals(3, visible.size)
    assertEquals("automotive_3", visible[2].id)

    db.close()
  }

  @Test
  fun `verify all categories have levels seeded properly and level 1 visible`() = runBlocking {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val db = Room.inMemoryDatabaseBuilder(context, QuizDatabase::class.java).allowMainThreadQueries().build()
    val repo = QuizRepository(db.quizDao())
    repo.initializeDefaultsIfNeeded()

    assertEquals(13, PackCategory.entries.size)

    for (pack in PackCategory.entries) {
      val packLevels = com.example.data.QuizPackData.getLevelsForPack(pack.id)
      assertEquals("Pack ${pack.id} size mismatch", 10, packLevels.size)
      // Level 1 should be free & visible initially
      val visible = com.example.data.QuizPackData.getVisibleLevelsForPack(pack.id, repo.allProgress.value)
      assertEquals("Pack ${pack.id} should show only Level 1 initially", 1, visible.size)
      assertEquals("${pack.id}_1", visible[0].id)
    }

    db.close()
  }
}
