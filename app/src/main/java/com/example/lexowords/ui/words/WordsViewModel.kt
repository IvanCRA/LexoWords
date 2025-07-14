package com.example.lexowords.ui.words

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.lexowords.domain.model.Word
import com.example.lexowords.domain.repository.WordRepository

class WordsViewModel(
    private val repository: WordRepository,
) : ViewModel() {
    private var _wordsList =
        mutableStateListOf<Word>().apply {
            addAll(repository.getAllWords())
        }
    var currentIndex by mutableStateOf(0)
        private set

    val currentWord: Word?
        get() = _wordsList.getOrNull(currentIndex)

    var showTranslation by mutableStateOf(false)
        private set

    fun onCardClick() {
        showTranslation = true
    }

    fun onAnswer(gotRight: Boolean) {
        if (_wordsList.isEmpty()) return

        if (gotRight) {
            _wordsList.removeAt(currentIndex)
            if (_wordsList.isEmpty()) {
                currentIndex = 0
                showTranslation = false
                return
            }
            if (currentIndex >= _wordsList.size) {
                currentIndex = 0
            }
        } else {
            currentIndex = (currentIndex + 1) % _wordsList.size
        }

        showTranslation = false
    }

    /* fun onFavoriteClick(wordId: Int) {
         repository.toggleFavorite(wordId)
         words = repository.getAllWords()
     }*/
}
