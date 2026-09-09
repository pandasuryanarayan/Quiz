package com.example

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
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
  fun `verify quiz packs catalog`() {
    val levels = com.example.data.QuizPackData.allLevels
    assertEquals(40, levels.size)
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
}
