package com.example.lexowords

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.lexowords.worker.RepeatWordsWorker
import com.example.lexowords.worker.ResetProgressWorker
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class LexoWordsApp : Application(), Configuration.Provider {
    @Inject lateinit var workerFactory: HiltWorkerFactory

    override val workManagerConfiguration: Configuration
        get() =
            Configuration.Builder()
                .setWorkerFactory(workerFactory)
                .build()

    override fun onCreate() {
        super.onCreate()
        WorkManager.initialize(this, workManagerConfiguration)
        scheduleResetProgressWorker()
        scheduleRepeatWorker()
    }

    private fun scheduleRepeatWorker() {
        val request =
            PeriodicWorkRequestBuilder<RepeatWordsWorker>(
                1,
                TimeUnit.HOURS,
            ).build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "repeat_words_worker",
            ExistingPeriodicWorkPolicy.KEEP,
            request,
        )
    }

    private fun scheduleResetProgressWorker() {
        val request =
            PeriodicWorkRequestBuilder<ResetProgressWorker>(
                8,
                TimeUnit.HOURS,
            ).build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "reset_progress_worker",
            ExistingPeriodicWorkPolicy.KEEP,
            request,
        )
    }
}
