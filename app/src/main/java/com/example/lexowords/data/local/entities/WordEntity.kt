package com.example.lexowords.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.lexowords.data.model.WordStudyState

@Entity(tableName = "Words")
data class WordEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val text: String,
    val translation: String,
    val transcription: String? = null,
    val isFavorite: Boolean = false,
    val addedAt: Long = System.currentTimeMillis(),
    val learned: Boolean = false,
    val nextReviewAt: Long? = null,
    val repetitions: Int = 0,
    val studyState: WordStudyState = WordStudyState.NEW,
)
