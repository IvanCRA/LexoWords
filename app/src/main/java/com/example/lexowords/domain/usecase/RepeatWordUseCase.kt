package com.example.lexowords.domain.usecase

import com.example.lexowords.data.model.WordStudyState
import com.example.lexowords.domain.repository.WordRepository
import javax.inject.Inject

class RepeatWordUseCase @Inject constructor(
    private val wordRepository: WordRepository,
) {
    suspend operator fun invoke() {
        val now = System.currentTimeMillis()
        val words = wordRepository.getDueWordsForReview(now)
        words.forEach {
            wordRepository.updateWordState(it.id, WordStudyState.REVIEW_LEARNING)
        }
    }
}
