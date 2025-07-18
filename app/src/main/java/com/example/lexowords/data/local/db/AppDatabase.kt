package com.example.lexowords.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.lexowords.data.local.dao.TagDao
import com.example.lexowords.data.local.dao.WordDao
import com.example.lexowords.data.local.entities.TagEntity
import com.example.lexowords.data.local.entities.WordEntity
import com.example.lexowords.data.local.entities.WordTagCrossRef

@Database(
    entities = [WordEntity::class, TagEntity::class, WordTagCrossRef::class],
    version = 2,
    exportSchema = false,
)
@TypeConverters(RoomConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao

    abstract fun tagDao(): TagDao
}
