package com.example.lexowords.domain.repository

import com.example.lexowords.domain.model.Word

interface WordRepository {
    fun getAllWords(): List<Word>

    fun toggleFavorite(wordId: Int)
}
