package com.example.lexowords.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.lexowords.data.local.entities.WordEntity
import com.example.lexowords.data.local.entities.WordTagCrossRef
import com.example.lexowords.data.local.relations.WordWithTags
import com.example.lexowords.data.model.WordStudyState
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWord(word: WordEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWords(words: List<WordEntity>)

    @Update
    suspend fun updateWord(word: WordEntity)

    @Delete
    suspend fun deleteWord(word: WordEntity)

    @Query("SELECT * FROM words")
    fun getAllWords(): Flow<List<WordEntity>>

    @Query("SELECT * FROM words WHERE isFavorite = 1")
    fun getFavoriteWords(): Flow<List<WordEntity>>

    @Query("SELECT * FROM words WHERE id = :id")
    fun getWordById(id: Int): WordEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCrossRef(crossRef: WordTagCrossRef)

    @Transaction
    @Query("DELETE FROM WORD_TAG_CROSS_REF WHERE wordId = :wordId")
    suspend fun deleteTagsByWordId(wordId: Int)

    @Transaction
    @Query("SELECT * FROM words")
    fun getWordsWithTags(): Flow<List<WordWithTags>>

    @Query("SELECT COUNT(*) FROM words")
    suspend fun getWordCount(): Int

    @Query("SELECT * FROM words")
    fun getAllWordsPaging(): PagingSource<Int, WordEntity>

    @Query("SELECT * FROM words LIMIT 1")
    suspend fun getAnyWord(): WordEntity?

    @Query("SELECT * FROM words WHERE studyState = :state ORDER BY RANDOM() LIMIT :limit")
    suspend fun getWordsByState(state: WordStudyState, limit: Int): List<WordEntity>

    @Query("UPDATE words SET studyState = :newState WHERE id = :wordId")
    suspend fun updateWordState(wordId: Int, newState: WordStudyState)

    @Query(
        """
    SELECT COUNT(*) FROM words
    WHERE studyState = :state
    AND date(addedAt / 1000, 'unixepoch') = date('now')
""",
    )
    suspend fun getLearningWordsCountToday(state: WordStudyState = WordStudyState.LEARNING): Int

    @Query("UPDATE words SET studyState = :newState, nextReviewAt = :timestamp WHERE id = :wordId")
    suspend fun updateWordStateWithTimestamp(wordId: Int, newState: WordStudyState, timestamp: Long)

    @Query("SELECT * FROM Words WHERE studyState = :state AND nextReviewAt <= :currentTime")
    suspend fun getWordsForTodayReview(
        state: WordStudyState = WordStudyState.REVIEW_LEARNING,
        currentTime: Long,
    ): List<WordEntity>

    @Query(
        """
        UPDATE words
        SET repetitions = :repetitions,
            interval = :interval,
            easeFactor = :easeFactor,
            nextReviewAt = :nextReviewAt,
            studyState = :newState
        WHERE id = :wordId
    """,
    )
    suspend fun updateRepetitionInfo(
        wordId: Int,
        repetitions: Int,
        interval: Int,
        easeFactor: Float,
        nextReviewAt: Long,
        newState: WordStudyState = WordStudyState.TO_REVIEW,
    )

    @Query("SELECT * FROM Words WHERE studyState = 'TO_REVIEW' AND nextReviewAt <= :now")
    suspend fun getDueWordsForReview(now: Long): List<WordEntity>

    @Query("SELECT * FROM words WHERE studyState = :state ORDER BY id DESC")
    fun observeWordByState(state: WordStudyState): Flow<List<WordEntity>>

    data class StudyStateCount(
        val state: String,
        val count: Int,
    )

    @Query("SELECT studyState as state, COUNT(*) as count FROM Words GROUP BY studyState")
    fun observeCountsByState(): Flow<List<StudyStateCount>>

    @Query("SELECT COUNT(*) FROM Words WHERE isFavorite = 1")
    fun observeFavoritesCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM Words WHERE studyState = 'TO_REVIEW' AND nextReviewAt <= :now")
    fun observeDueCount(now: Long): Flow<Int>

    @Query("SELECT COUNT(*) FROM Words")
    fun observeTotalWordsCount(): Flow<Int>
}
