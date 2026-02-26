package com.example.lexowords.domain.usecase

import com.example.lexowords.data.model.WordStudyState
import com.example.lexowords.domain.model.Word
import com.example.lexowords.domain.repository.WordRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test

private class FakeWordRepository : WordRepository {
    data class UpdateCall(
        val wordId: Int,
        val repetitions: Int,
        val interval: Int,
        val easeFactor: Float,
        val nextReviewAt: Long,
        val newState: WordStudyState,
    )

    var lastUpdate: UpdateCall? = null

    override fun getAllWords(): List<Word> = emptyList()

    override fun toggleFavorite(wordId: Int) = Unit

    override suspend fun getTodayLearningCount(): Int = 0

    override suspend fun getWordsByState(state: WordStudyState, limit: Int): List<Word> = emptyList()

    override suspend fun updateWordState(wordId: Int, newState: WordStudyState) = Unit

    override suspend fun updateWordStateWithTimestamp(wordId: Int, newState: WordStudyState, timestamp: Long) = Unit

    override suspend fun getWordsForTodayReview(currentTime: Long): List<Word> = emptyList()

    override suspend fun getDueWordsForReview(currentTime: Long): List<Word> = emptyList()

    override suspend fun updateRepetitionInfo(
        wordId: Int,
        repetitions: Int,
        interval: Int,
        easeFactor: Float,
        nextReviewAt: Long,
        newState: WordStudyState,
    ) {
        lastUpdate = UpdateCall(wordId, repetitions, interval, easeFactor, nextReviewAt, newState)
    }

    override fun observeWordByState(state: WordStudyState) = kotlinx.coroutines.flow.flowOf(emptyList<Word>())
}

class ProcessReviewAnswerUseCaseTest {
    @Test
    fun `answerQuality 5 increases repetition and schedules next review`() =
        runTest {
            val repo = FakeWordRepository()
            val useCase = ProcessReviewAnswerUseCase(repo)

            val now = System.currentTimeMillis()

            useCase(
                wordId = 1,
                currentRepetition = 0,
                currentInterval = 1,
                currentEaseFactor = 2.5f,
                answerQuality = 5,
                wordState = WordStudyState.TO_REVIEW,
            )

            val call = repo.lastUpdate ?: error("updateRepetitionInfo was not called")

            assertEquals(1, call.wordId)
            assertTrue("repetitions should be >= 1", call.repetitions >= 1)
            assertTrue("interval(hours) should be > 0", call.interval > 0)
            assertTrue("easeFactor should be >= 1.3", call.easeFactor >= 1.3f)
            assertTrue("nextReviewAt should be in the future", call.nextReviewAt > now)
            assertEquals(WordStudyState.TO_REVIEW, call.newState)
        }

    @Test
    fun `answerQuality 2 resets repetition to 0 and interval to 2 hours`() =
        runTest {
            val repo = FakeWordRepository()
            val useCase = ProcessReviewAnswerUseCase(repo)

            useCase(
                wordId = 7,
                currentRepetition = 5,
                currentInterval = 72,
                currentEaseFactor = 2.0f,
                answerQuality = 2,
                wordState = WordStudyState.REVIEW_LEARNING,
            )

            val call = repo.lastUpdate ?: error("updateRepetitionInfo was not called")

            assertEquals(7, call.wordId)
            assertEquals(0, call.repetitions)
            assertEquals(2, call.interval) // у тебя в usecase: 0 to 2 (2 часа)
            assertTrue(call.easeFactor >= 1.3f)
            assertEquals(WordStudyState.REVIEW_LEARNING, call.newState)
        }
}
