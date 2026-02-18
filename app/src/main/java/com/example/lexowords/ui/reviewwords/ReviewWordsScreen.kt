package com.example.lexowords.ui.reviewwords

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lexowords.domain.model.Word
import com.example.lexowords.ui.element.LexoWordCard

@Composable
fun ReviewWordsScreen(viewModel: ReviewWordsViewModel = hiltViewModel()) {
    val word by viewModel.currentWord.collectAsState()

    Scaffold { padding ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
        ) {
            word?.let {
                LexoWordCard(it)

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = viewModel::onRemembered,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text("Вспомнил")
                }

                Button(
                    onClick = viewModel::onForgot,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                ) {
                    Text("Не вспомнил")
                }
            } ?: Text(
                text = "Нет слов для повторения!",
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

@Composable
fun WordReviewCard(word: Word) {
    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = word.text, style = MaterialTheme.typography.titleLarge)
            Text(text = word.translation, style = MaterialTheme.typography.bodyLarge)
        }
    }
}
