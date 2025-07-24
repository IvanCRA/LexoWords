package com.example.lexowords.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.lexowords.domain.usecase.ResetProgressUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class ResetProgressWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val resetProgressUseCase: ResetProgressUseCase
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        resetProgressUseCase()
        return Result.success()
    }
}
