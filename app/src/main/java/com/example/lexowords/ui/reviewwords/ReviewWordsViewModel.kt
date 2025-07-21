package com.example.lexowords.ui.reviewwords

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
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
    private val wordRepository: WordRepository
) : ViewModel() {
    private val _wordsToReview = MutableStateFlow<List<Word>>(emptyList())
    val wordsToReview: StateFlow<List<Word>> = _wordsToReview

    init {
        loadWordsForReview()
    }

    private fun loadWordsForReview() {
        viewModelScope.launch {
            val words = wordRepository.getWordsForTodayReview()
            _wordsToReview.value = words
        }
    }

    fun markAsReviewed(word: Word) {
        viewModelScope.launch {
            wordRepository.updateWordState(word.id, WordStudyState.TO_REVIEW)
            loadWordsForReview()
        }
    }
}
