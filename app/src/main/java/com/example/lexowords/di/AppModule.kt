package com.example.lexowords.di

import android.app.Application
import androidx.room.Room
import com.example.lexowords.data.local.dao.TagDao
import com.example.lexowords.data.local.dao.UserProfileDao
import com.example.lexowords.data.local.dao.WordDao
import com.example.lexowords.data.local.db.AppDatabase
import com.example.lexowords.data.local.db.DatabaseInitializer
import com.example.lexowords.data.local.db.ProfileInitializer
import com.example.lexowords.data.repository.WordRepositoryImpl
import com.example.lexowords.domain.repository.UserProfileRepository
import com.example.lexowords.domain.repository.WordRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(
        app: Application,
    ): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "lexo_words_db",
        )   .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideWordDao(database: AppDatabase): WordDao {
        return database.wordDao()
    }

    @Provides
    @Singleton
    fun provideTagDao(database: AppDatabase): TagDao {
        return database.tagDao()
    }

    @Provides
    @Singleton
    fun provideUserProfileDao(database: AppDatabase): UserProfileDao {
        return database.userProfileDao()
    }

    @Provides
    fun provideProfileInitializer(
        repository: UserProfileRepository,
    ): ProfileInitializer {
        return ProfileInitializer(repository)
    }

    @Provides
    fun provideDatabaseInitializer(
        app: Application,
        wordDao: WordDao,
        tagDao: TagDao,
        profileInitializer: ProfileInitializer,
    ): DatabaseInitializer {
        return DatabaseInitializer(app, wordDao, tagDao, profileInitializer)
    }

    @Provides
    fun provideWordRepository(wordDao: WordDao): WordRepository {
        return WordRepositoryImpl(wordDao)
    }

    @Provides
    fun provideUserProfileRepository(dao: UserProfileDao): UserProfileRepository {
        return UserProfileRepository(dao)
    }
}
