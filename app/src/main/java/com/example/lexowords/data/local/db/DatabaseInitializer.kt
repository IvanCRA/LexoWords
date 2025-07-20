package com.example.lexowords.data.local.db

import android.content.Context
import com.example.lexowords.data.json.TagJson
import com.example.lexowords.data.json.WordJson
import com.example.lexowords.data.local.dao.TagDao
import com.example.lexowords.data.local.dao.WordDao
import com.example.lexowords.data.local.entities.TagEntity
import com.example.lexowords.data.local.entities.WordEntity
import com.example.lexowords.data.local.entities.WordTagCrossRef
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DatabaseInitializer(
    private val context: Context,
    private val wordDao: WordDao,
    private val tagDao: TagDao,
    private val profileInitializer: ProfileInitializer
) {
    suspend fun initialize() =
        withContext(Dispatchers.IO) {
            if (wordDao.getWordCount() > 0) return@withContext

            val gson = Gson()

            val tagJson = context.assets.open("tag_data.json").bufferedReader().use { it.readText() }
            val tags: List<TagJson> = gson.fromJson(tagJson, object : TypeToken<List<TagJson>>() {}.type)
            val tagEntities =
                tags.mapIndexed { index, tag ->
                    TagEntity(
                        id = index + 1,
                        code = tag.code,
                        name = tag.name,
                        transcription = tag.transcription,
                        translation = tag.translation.toString(),
                    )
                }
            tagDao.insertTag(tagEntities)

            val tagMap = tags.associateBy { it.code }
            val nameToId = tagEntities.associateBy { it.name }

            val wordJson = context.assets.open("dictionary_data.json").bufferedReader().use { it.readText() }
            val words: List<WordJson> = gson.fromJson(wordJson, object : TypeToken<List<WordJson>>() {}.type)

            words.forEachIndexed { index, wordJson ->
                val wordId =
                    wordDao.insertWord(
                        WordEntity(
                            id = index + 1,
                            text = wordJson.word,
                            translation = wordJson.translation,
                            transcription = wordJson.transcription,
                        ),
                    )
                wordJson.tags?.forEach { tagCode ->
                    val tagName = tagMap[tagCode]?.name ?: return@forEach
                    val tagId = nameToId[tagName]?.id ?: return@forEach
                    wordDao.insertCrossRef(WordTagCrossRef(wordId.toInt(), tagId))
                }
            }
        }

    suspend fun initializeIfNeeded() =
        withContext(Dispatchers.IO) {
            val alreadyInitialized = wordDao.getAnyWord() != null
            if (!alreadyInitialized) {
                initialize()
            }
            profileInitializer.initialize()
        }
}
