package com.example.lexowords.domain.model

import com.example.lexowords.data.model.WordStudyState

data class Word(
    val id: Int,
    val text: String,
    val translation: String,
    val isFavorite: Boolean = false,
    val studyState: WordStudyState,
)
