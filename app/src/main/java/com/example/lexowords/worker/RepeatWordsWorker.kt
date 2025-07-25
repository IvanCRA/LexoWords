package com.example.lexowords.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.lexowords.domain.usecase.PrepareWordsForReviewUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class RepeatWordsWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val prepareWordsForReviewUseCase: PrepareWordsForReviewUseCase,
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        showNotification("LexoWords", "RepeatWordsWorker запущен")
        return try {
            prepareWordsForReviewUseCase()
            Result.success()
        } catch (e: Exception) {
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
