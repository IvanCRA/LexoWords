package com.example.lexowords.data.local.entities

import androidx.room.Entity

@Entity(
    tableName = "word_tag_cross_ref",
    primaryKeys = ["wordId", "tagId"],
)
data class WordTagCrossRef(
    val wordId: Int,
    val tagId: Int,
)
