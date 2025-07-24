package com.example.lexowords.domain.usecase

import com.example.lexowords.domain.repository.WordRepository
import javax.inject.Inject
import kotlin.math.roundToInt

class ProcessReviewAnswerUseCase @Inject constructor(
    private val wordRepository: WordRepository
) {
    suspend operator fun invoke(
        wordId: Int,
        currentRepetition: Int,
        currentInterval: Int,
        currentEaseFactor: Float,
        answerQuality: Int
    ) {
        var newEF = currentEaseFactor + (0.1f - (5 - answerQuality) * (0.08f + (5 - answerQuality) * 0.02f))
        if (newEF < 1.3f) newEF = 1.3f

        val (newRep, newIntv) = if (answerQuality < 3) {
            0 to 1
        } else {
            val rep = currentRepetition + 1
            val intv = when(rep) {
                1 -> 1
                2 -> 6
                else -> (currentInterval * newEF).roundToInt()
            }
            rep to intv
        }

        val nextReviewAt = System.currentTimeMillis() + newIntv * 86_400_000L

        wordRepository.updateRepetitionInfo(
            wordId = wordId,
            repetitions = newRep,
            interval = newIntv,
            easeFactor = newEF,
            nextReviewAt = nextReviewAt)
    }
}
