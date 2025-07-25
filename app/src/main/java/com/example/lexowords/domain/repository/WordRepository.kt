package com.example.lexowords.domain.repository

import com.example.lexowords.domain.model.Word
import com.example.lexowords.data.model.WordStudyState

interface WordRepository {
    fun getAllWords(): List<Word>

    fun toggleFavorite(wordId: Int)

    suspend fun getTodayLearningCount(): Int

    suspend fun getWordsByState(state: WordStudyState, limit: Int): List<Word>

    suspend fun updateWordState(wordId: Int, newState: WordStudyState)

    suspend fun updateWordStateWithTimestamp(wordId: Int, newState: WordStudyState, timestamp: Long)

    suspend fun getWordsForTodayReview(currentTime: Long): List<Word>

    suspend fun getDueWordsForReview(currentTime: Long): List<Word>

    suspend fun updateRepetitionInfo(
        wordId: Int,
        repetitions: Int,
        interval: Int,
        easeFactor: Float,
        nextReviewAt: Long,
        newState: WordStudyState,
    )
}
