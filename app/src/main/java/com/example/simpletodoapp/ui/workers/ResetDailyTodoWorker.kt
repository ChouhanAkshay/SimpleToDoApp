package com.example.simpletodoapp.ui.workers

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.simpletodoapp.data.dataStore.usecase.ResetDailyTodoUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class ResetDailyTodoWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val resetDailyTodoUseCase: ResetDailyTodoUseCase
) : CoroutineWorker(context, workerParameters) {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun doWork(): Result {
        resetDailyTodoUseCase.process()

        //todo save current date of updated data in preference is updated do not update again for today
        return Result.success()
    }
}