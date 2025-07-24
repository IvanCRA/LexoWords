package com.example.lexowords.domain.usecase

import com.example.lexowords.data.model.WordStudyState
import com.example.lexowords.domain.repository.WordRepository
import javax.inject.Inject

class ResetProgressUseCase @Inject constructor(
    private val wordRepository: WordRepository
) {
    suspend operator fun invoke() {
        WordStudyState.entries.forEach { state ->
            val words = wordRepository.getWordsByState(state, Int.MAX_VALUE)
            words.forEach {
                wordRepository.updateWordState(it.id, WordStudyState.NEW)
            }
        }
    }
}
