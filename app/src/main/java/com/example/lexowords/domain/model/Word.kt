package com.example.lexowords.domain.model

data class Word(
    val id: Int,
    val text: String,
    val translation: String,
    val isFavorite: Boolean = false,
)
