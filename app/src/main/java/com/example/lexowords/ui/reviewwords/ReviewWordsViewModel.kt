package com.example.lexowords.ui.reviewwords

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lexowords.data.model.WordStudyState
import com.example.lexowords.domain.model.Word
import com.example.lexowords.domain.repository.WordRepository
import com.example.lexowords.domain.usecase.ProcessReviewAnswerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewWordsViewModel @Inject constructor(
    private val wordRepository: WordRepository,
    private val processReviewAnswerUseCase: ProcessReviewAnswerUseCase,
) : ViewModel() {
    private val reviewQueue = mutableStateListOf<Word>()
    private val _currentWord = MutableStateFlow<Word?>(null)
    val currentWord: StateFlow<Word?> = _currentWord

    private val forgetCountMap = mutableMapOf<Int, Int>()

    init {
        loadWordsForReview()
    }

    private fun loadWordsForReview() {
        viewModelScope.launch {
            val words = wordRepository.getWordsForTodayReview(System.currentTimeMillis())
            reviewQueue.clear()
            reviewQueue.addAll(words)
            _currentWord.value = reviewQueue.firstOrNull()
        }
    }

    private fun moveToNext() {
        val current = _currentWord.value ?: return
        reviewQueue.removeAll { it.id == current.id }
        _currentWord.value = reviewQueue.firstOrNull()
    }

    fun onForgot() {
        val word = currentWord.value ?: return
        viewModelScope.launch {
            val forgetCount = forgetCountMap.getOrDefault(word.id, 0) + 1
            forgetCountMap[word.id] = forgetCount

            val answerQuality = (5 - forgetCount).coerceIn(1, 2)

            processReviewAnswerUseCase(
                wordId = word.id,
                currentRepetition = word.repetitions,
                currentInterval = word.interval,
                currentEaseFactor = word.easeFactor,
                answerQuality = answerQuality,
                wordState = WordStudyState.REVIEW_LEARNING,
            )

            reviewQueue.removeFirstOrNull()
            reviewQueue.add(word)
            _currentWord.value = reviewQueue.firstOrNull()
        }
    }

    fun onRemembered() {
        val word = currentWord.value ?: return
        viewModelScope.launch {
            processReviewAnswerUseCase(
                wordId = word.id,
                currentRepetition = word.repetitions,
                currentInterval = word.interval,
                currentEaseFactor = word.easeFactor,
                answerQuality = 5,
                wordState = WordStudyState.TO_REVIEW,
            )
            forgetCountMap.remove(word.id)
            moveToNext()
        }
    }
}
