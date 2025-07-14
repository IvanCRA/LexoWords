package com.example.lexowords.ui.words

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.lexowords.domain.repository.WordRepository

class WordsViewModel(
    private val repository: WordRepository,
) : ViewModel() {
    var words by mutableStateOf(repository.getAllWords())
        private set

    fun onFavoriteClick(wordId: Int) {
        repository.toggleFavorite(wordId)
        words = repository.getAllWords()
    }
}
