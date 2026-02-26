package com.example.lexowords.ui

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.lexowords.MainActivity
import org.junit.Rule
import org.junit.Test

class NavigationSmokeTest {
    @get:Rule
    val rule = createAndroidComposeRule<MainActivity>()

    @Test
    fun openReviewFromMain() {
        rule.onNodeWithText("Повторить слова").performClick()
        rule.onNodeWithText("Посмотреть слова").assertExists()
    }
}
