package com.example.lexowords.data.local.modelmapper

import com.example.lexowords.data.local.entities.WordEntity
import com.example.lexowords.domain.model.Word

fun WordEntity.toDomain(): Word =
    Word(
        id = this.id,
        text = this.text,
        translation = this.translation,
        transcription = this.transcription,
        isFavorite = this.isFavorite,
        addedAt = this.addedAt,
        learned = this.learned,
        nextReviewAt = this.nextReviewAt,
        repetitions = this.repetitions,
        interval = this.interval,
        easeFactor = this.easeFactor,
        studyState = this.studyState,
    )

fun Word.toEntity(): WordEntity =
    WordEntity(
        id = this.id,
        text = this.text,
        translation = this.translation,
        transcription = this.transcription,
        isFavorite = this.isFavorite,
        addedAt = this.addedAt,
        learned = this.learned,
        nextReviewAt = this.nextReviewAt,
        repetitions = this.repetitions,
        interval = this.interval,
        easeFactor = this.easeFactor,
        studyState = this.studyState,
    )
