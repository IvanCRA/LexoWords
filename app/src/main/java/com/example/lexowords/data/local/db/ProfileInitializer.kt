package com.example.lexowords.data.local.db

import com.example.lexowords.domain.repository.UserProfileRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileInitializer @Inject constructor(
    private val repository: UserProfileRepository,
) {
    suspend fun initialize() {
        repository.ensureProfileExists()
        repository.updateStreakIfNeeded()
    }
}
