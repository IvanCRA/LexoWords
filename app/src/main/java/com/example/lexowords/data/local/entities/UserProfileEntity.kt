package com.example.lexowords.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserProfileEntity (
    @PrimaryKey val id: Int = 1,
    val dailyLimit: Int = 10,
    val totalLearned: Int = 0,
    val totalRepeated: Int = 0,
    val lastStudiedAt: Long = 0L,
    val currentStreak: Int = 0,
    val longestStreak: Int = 0,
    val wordsLearnedToday: Int = 0,
    val wordsRepeatedToday: Int = 0
)

