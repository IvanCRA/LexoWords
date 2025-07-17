package com.example.lexowords.data.local.db

import androidx.room.TypeConverter
import com.example.lexowords.data.model.WordStudyState

class RoomConverters {
    @TypeConverter
    fun fromStudyState(state: WordStudyState): String = state.name

    @TypeConverter
    fun toStudyState(name: String): WordStudyState = WordStudyState.valueOf(name)
}
