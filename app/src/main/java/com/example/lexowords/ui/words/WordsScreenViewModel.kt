package com.example.lexowords.ui.words

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lexowords.data.model.WordStudyState
import com.example.lexowords.domain.repository.WordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class WordsScreenViewModel @Inject constructor(
    private val repo: WordRepository,
) : ViewModel() {
    val toReviewWords =
        repo.observeWordByState(WordStudyState.TO_REVIEW)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val learnedWords =
        repo.observeWordByState(WordStudyState.LEARNED)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
}
