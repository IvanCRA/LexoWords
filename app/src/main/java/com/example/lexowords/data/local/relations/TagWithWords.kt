package com.example.lexowords.data.local.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.lexowords.data.local.entities.TagEntity
import com.example.lexowords.data.local.entities.WordEntity
import com.example.lexowords.data.local.entities.WordTagCrossRef

data class TagWithWords(
    @Embedded val tag: TagEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy =
            Junction(
                value = WordTagCrossRef::class,
                parentColumn = "tagId",
                entityColumn = "wordId",
            ),
    )
    val words: List<WordEntity>,
)
