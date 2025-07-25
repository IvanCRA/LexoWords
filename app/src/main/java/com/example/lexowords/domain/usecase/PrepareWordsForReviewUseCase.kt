package com.example.lexowords.domain.usecase

import com.example.lexowords.data.local.dao.WordDao
import com.example.lexowords.data.model.WordStudyState
import javax.inject.Inject

class PrepareWordsForReviewUseCase @Inject constructor(
    private val wordDao: WordDao,
) {
    suspend operator fun invoke() {
        val now = System.currentTimeMillis()
        val dueWords = wordDao.getDueWordsForReview(now)
        dueWords.forEach { word ->
            wordDao.updateWordState(word.id, WordStudyState.REVIEW_LEARNING)
        }
    }
}
