package com.example.lexowords.ui.studywords

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.lexowords.domain.model.Word
import androidx.compose.runtime.getValue

@Composable
fun StudyWordsScreen(viewModel: StudyWordsViewModel = hiltViewModel()) {
    val word by viewModel.currentWord.collectAsState()
    val limitReached by viewModel.limitReached.collectAsState()
    val progress = viewModel.progress.collectAsState()
    val (current, total) = progress.value

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(limitReached) {
        if (limitReached) {
            snackbarHostState.showSnackbar("Лимит 10 слов в день достигнут")
        }
    }

    Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            word?.let {
                WordCard(it)

                if (total > 0) {
                    Text(
                        text = "Прогресс: $current из $total",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Button(
                    onClick = { viewModel.onKnowWord() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    Text("Знаю")
                }

                Button(
                    onClick = { viewModel.onDontKnowWord() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    Text("Не знаю")
                }
            } ?: Text(
                text = "Нет доступных слов или лимит достигнут",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
fun WordCard(word: Word) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = word.text, style = MaterialTheme.typography.titleLarge)
            Text(text = word.translation, style = MaterialTheme.typography.bodyLarge)
        }
    }
}
