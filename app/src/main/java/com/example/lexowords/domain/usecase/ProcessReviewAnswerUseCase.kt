package com.example.lexowords.domain.usecase

import com.example.lexowords.data.model.WordStudyState
import com.example.lexowords.domain.repository.WordRepository
import javax.inject.Inject
import kotlin.math.roundToInt

class ProcessReviewAnswerUseCase @Inject constructor(
    private val wordRepository: WordRepository,
) {
    suspend operator fun invoke(
        wordId: Int,
        currentRepetition: Int,
        currentInterval: Int,
        currentEaseFactor: Float,
        answerQuality: Int,
        wordState: WordStudyState,
    ) {
        var newEF = currentEaseFactor + (0.1f - (5 - answerQuality) * (0.08f + (5 - answerQuality) * 0.02f))
        if (newEF < 1.3f) newEF = 1.3f

        val (newRep, newIntvHours) = if (answerQuality < 3) {
            0 to 2 // сброс к 2 часам
        } else {
            val rep = currentRepetition + 1
            val intv = when (rep) {
                1 -> 2            // 2 часа
                2 -> 8            // 8 часов
                3 -> 24           // 1 день
                4 -> 72           // 3 дня
                else -> (currentInterval * newEF).roundToInt()
            }
            rep to intv
        }

        val nextReviewAt = System.currentTimeMillis() + newIntvHours * 60 * 60 * 1000L

        wordRepository.updateRepetitionInfo(
            wordId = wordId,
            repetitions = newRep,
            interval = newIntvHours,
            easeFactor = newEF,
            nextReviewAt = nextReviewAt,
            newState = wordState
        )
    }
}

