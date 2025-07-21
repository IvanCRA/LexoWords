package com.example.lexowords.data.repository

import com.example.lexowords.data.local.dao.WordDao
import com.example.lexowords.data.local.modelmapper.toDomain
import com.example.lexowords.data.model.WordStudyState
import com.example.lexowords.domain.model.Word
import com.example.lexowords.domain.repository.WordRepository
import javax.inject.Inject

class WordRepositoryImpl @Inject constructor(
    private val wordDao: WordDao,
) : WordRepository {
    override fun getAllWords(): List<Word> {
        // Можно оставить пустым или заглушкой, если не нужен
        return emptyList()
    }

    override fun toggleFavorite(wordId: Int) {
        // Здесь может быть доработка под реальные фичи позже
    }

    override suspend fun getTodayLearningCount(): Int {
        return wordDao.getLearningWordsCountToday(WordStudyState.LEARNING)
    }

    override suspend fun getWordsByState(state: WordStudyState, limit: Int): List<Word> {
        return wordDao.getWordsByState(state, limit).map { it.toDomain() }
    }

    override suspend fun updateWordState(wordId: Int, newState: WordStudyState) {
        wordDao.updateWordState(wordId, newState)
    }

    override suspend fun updateWordStateWithTimestamp(wordId: Int, newState: WordStudyState, timestamp: Long) {
        wordDao.updateWordStateWithTimestamp(wordId, newState, timestamp)
    }

    override suspend fun getWordsForTodayReview(): List<Word> {
        val now = System.currentTimeMillis()
        return wordDao.getWordsForTodayReview(now).map {it.toDomain()}
    }
}
