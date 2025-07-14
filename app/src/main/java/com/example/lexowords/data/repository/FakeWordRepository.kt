package com.example.lexowords.data.repository

import androidx.compose.runtime.mutableStateListOf
import com.example.lexowords.domain.model.Word
import com.example.lexowords.domain.repository.WordRepository

class FakeWordRepository : WordRepository {
    private val words =
        mutableStateListOf(
            Word(1, "apple", "яблоко"),
            Word(2, "book", "книга"),
            Word(3, "cat", "кот"),
        )

    override fun getAllWords(): List<Word> = words

    override fun toggleFavorite(wordId: Int) {
        val index = words.indexOfFirst { it.id == wordId }
        if (index >= 0) {
            val word = words[index]
            words[index] = word.copy(isFavorite = !word.isFavorite)
        }
    }
}
