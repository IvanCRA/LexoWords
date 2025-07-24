package com.example.lexowords.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.lexowords.data.local.dao.WordDao
import com.example.lexowords.data.model.WordStudyState
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class RepeatWordsWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val wordDao: WordDao,
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        showNotification("LexoWords", "RepeatWordsWorker запущен")
        return try {
            Log.d("RepeatWorker", "Worker STARTED")
            val now = System.currentTimeMillis()
            val dueWords = wordDao.getDueWordsForReview(now)
            Log.d("RepeatWorker", "Words to update: ${dueWords.size}")
            dueWords.forEach { word ->
                wordDao.updateWordState(word.id, WordStudyState.REVIEW_LEARNING)
            }
            Result.success()
        } catch (e: Exception) {
            Log.e("RepeatWorker", "WORKER ERROR: ${e.message}", e)
            Result.failure()
        }
    }

    private fun showNotification(title: String, message: String) {
        val channelId = "repeat_worker_channel"
        val manager = getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(
                    channelId,
                    "Повтор слов",
                    NotificationManager.IMPORTANCE_LOW,
                )
            manager.createNotificationChannel(channel)
        }

        val notification =
            NotificationCompat.Builder(applicationContext, channelId)
                .setSmallIcon(android.R.drawable.ic_popup_reminder)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build()

        manager.notify(System.currentTimeMillis().toInt(), notification)
    }
}
