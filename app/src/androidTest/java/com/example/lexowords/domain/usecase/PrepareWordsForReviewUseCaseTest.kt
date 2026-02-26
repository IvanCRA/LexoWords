package com.example.lexowords.domain.usecase

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.lexowords.data.local.db.AppDatabase
import com.example.lexowords.data.local.entities.WordEntity
import com.example.lexowords.data.model.WordStudyState
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PrepareWordsForReviewUseCaseTest {
    private lateinit var db: AppDatabase

    @Before
    fun setup() {
        db =
            Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                AppDatabase::class.java,
            ).allowMainThreadQueries().build()
    }

    @After
    fun teardown() {
        db.close()
    }

    /**
     * Проверка: слова со state=TO_REVIEW и nextReviewAt <= now
     * после PrepareWordsForReviewUseCase() переходят в REVIEW_LEARNING
     * и попадают в выборку getWordsForTodayReview().
     */

    @Test
    fun dueToReviewWordsBecomeReviewLearning() =
        runTest {
            val dao = db.wordDao()
            val useCase = PrepareWordsForReviewUseCase(dao)

            val now = System.currentTimeMillis()

            val due =
                WordEntity(
                    text = "cat",
                    translation = "кот",
                    transcription = null,
                    isFavorite = false,
                    nextReviewAt = now - 1000,
                    repetitions = 0,
                    interval = 1,
                    easeFactor = 2.5f,
                    studyState = WordStudyState.TO_REVIEW,
                )

            val notDue =
                WordEntity(
                    text = "dog",
                    translation = "собака",
                    transcription = null,
                    isFavorite = false,
                    nextReviewAt = now + 60_000,
                    repetitions = 0,
                    interval = 1,
                    easeFactor = 2.5f,
                    studyState = WordStudyState.TO_REVIEW,
                )

            dao.insertWords(listOf(due, notDue))

            useCase()

            val reviewWords = dao.getWordsForTodayReview(currentTime = now)
            assertEquals(1, reviewWords.size)
            assertEquals("cat", reviewWords.first().text)
            assertEquals(WordStudyState.REVIEW_LEARNING, reviewWords.first().studyState)
        }
}
