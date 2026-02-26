package com.example.lexowords.domain.repository

import com.example.lexowords.data.local.dao.UserProfileDao
import com.example.lexowords.data.local.entities.UserProfileEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class UserProfileRepository @Inject constructor(
    private val dao: UserProfileDao,
) {
    val profile: Flow<UserProfileEntity?> = dao.getProfile()

    suspend fun ensureProfileExists() {
        val profile = dao.getProfile().firstOrNull()
        if (profile == null) {
            dao.insertOrUpdate(UserProfileEntity())
        }
    }

    suspend fun updateStreakIfNeeded() {
        val now = System.currentTimeMillis()
        val today = now / (1000 * 60 * 60 * 24)
        val profile = dao.getProfile().firstOrNull() ?: return

        val lastDay = profile.lastStudiedAt / (1000 * 60 * 60 * 24)

        if (today != lastDay) {
            val newStreak = if (today == lastDay + 1) profile.currentStreak + 1 else 1
            val longest = maxOf(profile.longestStreak, newStreak)

            dao.insertOrUpdate(
                profile.copy(
                    lastStudiedAt = now,
                    currentStreak = newStreak,
                    longestStreak = longest,
                    wordsLearnedToday = 0,
                    wordsRepeatedToday = 0,
                ),
            )
        }
    }

    suspend fun incrementLearnedToday() {
        val profile = dao.getProfileOnce() ?: return
        val updated =
            profile.copy(
                wordsLearnedToday = profile.wordsLearnedToday + 1,
                totalLearned = profile.totalLearned + 1,
            )
        dao.insertOrUpdate(updated)
    }

    suspend fun incrementRepeatedToday() {
        val profile = dao.getProfileOnce() ?: return
        val updated =
            profile.copy(
                wordsRepeatedToday = profile.wordsRepeatedToday + 1,
                totalRepeated = profile.totalRepeated + 1,
                lastStudiedAt = System.currentTimeMillis(),
            )
        dao.insertOrUpdate(updated)
    }

    suspend fun getProfileOnce(): UserProfileEntity? {
        return dao.getProfileOnce()
    }
}
