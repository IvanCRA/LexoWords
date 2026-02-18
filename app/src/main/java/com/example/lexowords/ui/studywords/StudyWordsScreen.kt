package com.example.lexowords.ui.studywords

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
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
import com.example.lexowords.data.local.entities.UserProfileEntity
import com.example.lexowords.ui.element.LexoWordCard

@Composable
fun StudyWordsScreen(viewModel: StudyWordsViewModel = hiltViewModel()) {
    val word by viewModel.currentWord.collectAsState()
    val limitReached by viewModel.limitReached.collectAsState()
    val profile by viewModel.profile.collectAsState()
    val (current, total) = viewModel.progress.collectAsState().value

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(limitReached) {
        if (limitReached) {
            snackbarHostState.showSnackbar("Лимит 10 слов в день достигнут")
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
    ) { padding ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
        ) {
            profile?.let {
                ProgressBarSection(profile = it)
            }

            Spacer(modifier = Modifier.height(16.dp))

            when {
                limitReached -> {
                    LimitReachedScreen()
                }

                word != null -> {
                    LexoWordCard(word!!)

                    if (viewModel.inLearningMode && total > 0) {
                        Text(
                            text = "Прогресс: $current из $total",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(top = 8.dp),
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    if (viewModel.inLearningMode) {
                        Button(
                            onClick = viewModel::onStillLearning,
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Text("Еще не выучил")
                        }

                        Button(
                            onClick = viewModel::onKnowWord,
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp),
                        ) {
                            Text("Выучено (на повторение)")
                        }
                    } else {
                        Button(
                            onClick = viewModel::onKnowWord,
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Text("Знаю")
                        }

                        Button(
                            onClick = viewModel::onDontKnowWord,
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp),
                        ) {
                            Text("Не знаю")
                        }
                    }
                }

                else -> {
                    Text(
                        text = "Нет доступных слов или всё выучено",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(top = 32.dp),
                    )
                }
            }
        }
    }
}

@Composable
fun WordCard(word: Word) {
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

@Composable
fun ProgressBarSection(profile: UserProfileEntity) {
    val animatedProgress by animateFloatAsState(
        targetValue = (profile.wordsLearnedToday / profile.dailyLimit.toFloat()).coerceIn(0f, 1f),
        animationSpec = tween(durationMillis = 300),
        label = "ProgressAnimation",
    )

    Column(modifier = Modifier.padding(bottom = 16.dp)) {
        Text(
            text = "Прогресс: ${profile.wordsLearnedToday} / ${profile.dailyLimit}",
            style = MaterialTheme.typography.bodyLarge,
        )
        LinearProgressIndicator(
            progress = animatedProgress,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
        )
        Text(
            text = "\uD83D\uDD25 Стрик: ${profile.currentStreak} дн. (макс: ${profile.longestStreak})",
            style = MaterialTheme.typography.bodySmall,
        )
    }
}

@Composable
fun LimitReachedScreen() {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(24.dp),
    ) {
        Text(
            text = "🎉 Вы уже выучили 10 слов сегодня!",
            style = MaterialTheme.typography.titleLarge,
        )
        Text(
            text = "Загляните завтра, чтобы продолжить изучение.",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 8.dp),
        )
    }
}
