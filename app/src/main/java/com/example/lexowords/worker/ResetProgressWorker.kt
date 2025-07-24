package com.example.lexowords.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.lexowords.data.local.dao.UserProfileDao
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.Calendar
import kotlin.math.max

@HiltWorker
class ResetProgressWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val userProfileDao: UserProfileDao,
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        return try {
            val profile = userProfileDao.getProfileOnce() ?: return Result.success()
            val today = getTodayMidnight()
            val yesterday = today - 24 * 60 * 60 * 1000

            val lastStudied = profile.lastStudiedAt
            val newStreak =
                if (lastStudied in yesterday until today) {
                    profile.currentStreak + 1
                } else {
                    0
                }

            val updatedProfile =
                profile.copy(
                    currentStreak = newStreak,
                    longestStreak = max(profile.longestStreak, newStreak),
                    wordsLearnedToday = 0,
                    wordsRepeatedToday = 0,
                )

            userProfileDao.update(updatedProfile)
            Log.d("ResetProgressWorker", "Streak reset complete.")
            Result.success()
        } catch (e: Exception) {
            Log.e("ResetProgressWorker", "Error resetting streak: ${e.message}", e)
            Result.failure()
        }
    }

    private fun getTodayMidnight(): Long {
        return Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
    }
}
