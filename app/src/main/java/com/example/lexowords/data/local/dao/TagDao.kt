package com.example.lexowords.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.lexowords.data.local.entities.TagEntity
import com.example.lexowords.data.local.relations.TagWithWords
import kotlinx.coroutines.flow.Flow

@Dao
interface TagDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTag(tag: TagEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTag(tags: List<TagEntity>)

    @Query("SELECT * FROM tags")
    fun getAllTags(): Flow<List<TagEntity>>

    @Query("SELECT * FROM tags WHERE id = :id")
    suspend fun getTagById(id: Int): TagEntity?

    @Transaction
    @Query("SELECT * FROM tags")
    fun getTagsWithWords(): Flow<List<TagWithWords>>
}
