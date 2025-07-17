package com.example.lexowords.domain.repository

import com.example.lexowords.domain.model.Word
import com.example.lexowords.data.model.WordStudyState

interface WordRepository {
    fun getAllWords(): List<Word>

    fun toggleFavorite(wordId: Int)

    suspend fun getTodayLearningCount(): Int

    suspend fun getWordsByState(state: WordStudyState, limit: Int): List<Word>

    suspend fun updateWordState(wordId: Int, newState: WordStudyState)
}
