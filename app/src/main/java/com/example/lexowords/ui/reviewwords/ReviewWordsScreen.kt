package com.example.lexowords.ui.reviewwords

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lexowords.domain.model.Word


@Composable
fun ReviewWordsScreen(viewModel: ReviewWordsViewModel = hiltViewModel()) {
    val words by viewModel.wordsToReview.collectAsState()
    var currentIndex by remember { mutableStateOf(0) }

    val currentWord = words.getOrNull(currentIndex)

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            if (currentWord != null) {
                WordReviewCard(word = currentWord)

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        viewModel.markAsReviewed(currentWord)
                        currentIndex++
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Повторено")
                }
            } else {
                Text(
                    "ГОЙДА ВСЕ СЛОВА ПОВТОРЕНЫ ГОЙДА",
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}

@Composable
fun WordReviewCard(word: Word) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = word.text, style = MaterialTheme.typography.titleLarge)
            Text(text = word.translation, style = MaterialTheme.typography.bodyLarge)
        }
    }
}
