package com.example.lexowords

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lexowords.ui.main.MainScreen
import com.example.lexowords.ui.navigation.NavRoutes
import com.example.lexowords.ui.reviewwords.ReviewWordsScreen
import com.example.lexowords.ui.splash.SplashScreen
import com.example.lexowords.ui.studywords.StudyWordsScreen
import com.example.lexowords.ui.theme.LexoWordsTheme
import dagger.hilt.android.AndroidEntryPoint
import android.Manifest

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

                val navController = rememberNavController()
                LexoWordsTheme {
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


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                            1001,
                        )
                    }
                }
            }
        }
    }
}


/*@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

}*/
