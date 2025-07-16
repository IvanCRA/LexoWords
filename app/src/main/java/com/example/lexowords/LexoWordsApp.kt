package com.example.lexowords

import android.app.Application
import androidx.room.Room
import com.example.lexowords.data.local.db.AppDatabase
import com.example.lexowords.data.local.db.DatabaseInitializer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LexoWordsApp : Application() {
    override fun onCreate() {
        super.onCreate()

        val db =
            Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java,
                "lexo_words_db",
            ).build()

        val initializer =
            DatabaseInitializer(
                context = applicationContext,
                wordDao = db.wordDao(),
                tagDao = db.tagDao(),
            )

        CoroutineScope(Dispatchers.IO).launch {
            initializer.initialize()
        }
    }
}
