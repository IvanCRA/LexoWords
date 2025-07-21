package com.example.lexowords.ui.reviewwords

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lexowords.data.model.WordStudyState
import com.example.lexowords.domain.model.Word
import com.example.lexowords.domain.repository.WordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewWordsViewModel @Inject constructor(
    private val wordRepository: WordRepository,
) : ViewModel() {
    private val reviewQueue = mutableStateListOf<Word>()
    private val _currentWord = MutableStateFlow<Word?>(null)
    val currentWord: StateFlow<Word?> = _currentWord

    init {
        loadWordsForReview()
    }

    private fun loadWordsForReview() {
        viewModelScope.launch {
            val words = wordRepository.getWordsForTodayReview()
            reviewQueue.clear()
            reviewQueue.addAll(words)
            _currentWord.value = reviewQueue.firstOrNull()
        }
    }

    private fun moveToNext() {
        reviewQueue.removeFirstOrNull()
        _currentWord.value = reviewQueue.firstOrNull()
    }

    fun onForgot() {
        val word = currentWord.value ?: return
        viewModelScope.launch {
            wordRepository.updateWordState(word.id, WordStudyState.REVIEW_LEARNING)
            reviewQueue.add(word)
            moveToNext()
        }
    }

    fun onRemembered() {
        val word = currentWord.value ?: return
        viewModelScope.launch {
            if (word.studyState == WordStudyState.REVIEW_LEARNING) {
                wordRepository.updateWordState(word.id, WordStudyState.TO_REVIEW)
            }
            moveToNext()
        }
    }
}
