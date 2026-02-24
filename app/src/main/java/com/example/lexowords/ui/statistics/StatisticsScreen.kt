package com.example.lexowords.ui.statistics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticsScreen(
    onBack: () -> Unit,
    vm: StatisticsViewModel = hiltViewModel(),
) {
    val state by vm.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Статистика") },
            )
        },
    ) { padding ->
        Column(
            modifier =
                Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            StatCard(
                title = "Стрик",
                lines =
                    listOf(
                        "Текущий: ${state.currentStreak}",
                        "Лучший: ${state.longestStreak}",
                    ),
            )

            StatCard(
                title = "Сегодня",
                lines =
                    listOf(
                        "Выучено: ${state.learnedToday} / ${state.dailyLimit}",
                        "Повторено: ${state.repeatedToday}",
                    ),
            )

            StatCard(
                title = "Всего",
                lines =
                    listOf(
                        "Выучено: ${state.totalLearned}",
                        "Повторено: ${state.totalRepeated}",
                    ),
            )

            StatCard(
                title = "Слова",
                lines =
                    listOf(
                        "Всего в базе: ${state.totalWords}",
                        "Избранные: ${state.favorites}",
                        "К повторению сейчас: ${state.dueNow}",
                    ),
            )

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("По состояниям", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(8.dp))
                    if (state.byState.isEmpty()) {
                        Text("Пока пусто")
                    } else {
                        state.byState.forEach { (name, count) ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Text(name)
                                Text(count.toString())
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun StatCard(title: String, lines: List<String>) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
            lines.forEach { Text(it) }
        }
    }
}
