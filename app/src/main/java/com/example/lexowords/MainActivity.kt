package com.example.lexowords

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.lexowords.ui.main.MainScreen
import com.example.lexowords.ui.navigation.NavRoutes
import com.example.lexowords.ui.reviewwords.ReviewWordsScreen
import com.example.lexowords.ui.splash.SplashScreen
import com.example.lexowords.ui.studywords.StudyWordsScreen
import com.example.lexowords.ui.theme.LexoWordsTheme
import com.example.lexowords.worker.RepeatWordsWorker
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val testRequest = OneTimeWorkRequestBuilder<RepeatWordsWorker>().build()
        WorkManager.getInstance(this).enqueue(testRequest)

        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            MaterialTheme {
                NavHost(navController = navController, startDestination = NavRoutes.SPLASH) {
                    composable(NavRoutes.SPLASH) {
                        SplashScreen(navController)
                    }
                    composable(NavRoutes.MAIN) {
                        MainScreen(navController)
                    }
                    composable(NavRoutes.STUDY) {
                        StudyWordsScreen()
                    }
                    composable(NavRoutes.REVIEW) {
                        ReviewWordsScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(
    name: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = "Hello $name!",
        modifier = modifier,
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LexoWordsTheme {
        Greeting("Android")
    }
}
