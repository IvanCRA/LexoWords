package com.example.lexowords.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.lexowords.data.local.dao.WordDao
import com.example.lexowords.data.model.WordStudyState
import com.example.lexowords.domain.usecase.RepeatWordUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class RepeatWordsWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val wordDao: WordDao
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
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
}
