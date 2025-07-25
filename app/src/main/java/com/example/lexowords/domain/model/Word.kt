package com.example.lexowords.domain.model

import com.example.lexowords.data.model.WordStudyState

data class Word(
    val id: Int,
    val text: String,
    val translation: String,
    val isFavorite: Boolean = false,
    val addedAt: Long,
    val learned: Boolean = false,
    val nextReviewAt: Long? = null,
    val repetitions: Int = 0,
    val interval: Int = 1,
    val easeFactor: Float = 2.5f,
    val studyState: WordStudyState,
)
