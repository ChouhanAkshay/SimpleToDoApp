package com.example.simpletodoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.simpletodoapp.navigation.MainActivityNavigation
import com.example.simpletodoapp.ui.theme.SimpleToDoAppTheme
import com.example.simpletodoapp.ui.viewmodels.MainViewModel
import com.example.simpletodoapp.ui.workers.ResetDailyTodoWorker
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val vm: MainViewModel = hiltViewModel()

            //reset's daily to-do's progress every day
            scheduleDailyToDoResetTask()

            SimpleToDoAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainActivityNavigation(vm)
                }
            }
        }
    }

    private fun scheduleDailyToDoResetTask() {
        val workManger = WorkManager.getInstance(this)
        val periodicWorkReq =
            PeriodicWorkRequest.Builder(ResetDailyTodoWorker::class.java, 15, TimeUnit.MINUTES).build()

        workManger
            .enqueueUniquePeriodicWork(PERIODIC_WORK_TAG,
                ExistingPeriodicWorkPolicy.KEEP,
                periodicWorkReq)
    }

    companion object {
        const val PERIODIC_WORK_TAG = "1234"
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SimpleToDoAppTheme {
        MainActivityNavigation(hiltViewModel())
    }
}