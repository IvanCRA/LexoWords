package com.example.lexowords.data.local.model_mapper

import com.example.lexowords.data.local.entities.WordEntity
import com.example.lexowords.domain.model.Word

fun WordEntity.toDomain(): Word = Word(
    id = this.id,
    text = this.text,
    translation = this.translation,
    isFavorite = this.isFavorite
)

fun Word.toEntity(): WordEntity = WordEntity(
    id = this.id,
    text = this.text,
    translation = this.translation,
    isFavorite = this.isFavorite,
)
