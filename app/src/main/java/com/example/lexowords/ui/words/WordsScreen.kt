package com.example.lexowords.ui.words

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.lexowords.domain.model.Word
import com.example.lexowords.ui.element.LexoWordCard
import com.example.lexowords.ui.theme.PurplePrimary
import com.example.lexowords.ui.theme.TextPrimary

enum class WordsTab { LEARNING, KNOWN }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordsScreen(viewModel: WordsScreenViewModel = hiltViewModel()) {
    var tab by remember { mutableStateOf(WordsTab.LEARNING) }
    var selectedWord by remember { mutableStateOf<Word?>(null) }

    val learning by viewModel.toReviewWords.collectAsState()
    val known by viewModel.learnedWords.collectAsState()

    val list = if (tab == WordsTab.LEARNING) learning else known

    Scaffold { padding ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
        ) {
            SecondaryTabRow(
                selectedTabIndex = if (tab == WordsTab.LEARNING) 0 else 1,
                modifier =
                    Modifier
                        .padding(vertical = 4.dp, horizontal = 8.dp)
                        .clip(RoundedCornerShape(50))
                        .padding(1.dp),
                indicator = { },
                divider = { },
            ) {
                val selectedLearning = tab == WordsTab.LEARNING
                Tab(
                    selected = selectedLearning,
                    onClick = { tab = WordsTab.LEARNING },
                    modifier =
                        Modifier
                            .clip(RoundedCornerShape(50))
                            .background(if (selectedLearning) PurplePrimary else Color.Transparent),
                    text = {
                        Text(
                            "Учу",
                            color = if (selectedLearning) Color.White else TextPrimary.copy(alpha = 0.7f),
                            style =
                                MaterialTheme.typography.labelMedium.copy(
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                ),
                        )
                    },
                )
                val selectedKnown = tab == WordsTab.KNOWN
                Tab(
                    selected = selectedKnown,
                    onClick = { tab = WordsTab.KNOWN },
                    modifier =
                        Modifier
                            .clip(RoundedCornerShape(50))
                            .background(if (selectedKnown) PurplePrimary else Color.Transparent),
                    text = {
                        Text(
                            "Знаю",
                            color = if (selectedKnown) Color.White else TextPrimary.copy(alpha = 0.7f),
                            style =
                                MaterialTheme.typography.labelMedium.copy(
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                ),
                        )
                    },
                )
            }

            Spacer(Modifier.height(12.dp))

            if (list.isEmpty()) {
                Text(
                    text =
                        if (tab == WordsTab.LEARNING) {
                            "Пока нет слов на повторении"
                        } else {
                            "Пока нет выученных слов"
                        },
                    style = MaterialTheme.typography.bodyLarge,
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(list, key = { it.id }) { w ->
                        WordRow(
                            word = w,
                            onClick = { selectedWord = w },
                        )
                    }
                }
            }
        }
    }

    if (selectedWord != null) {
        ModalBottomSheet(
            onDismissRequest = { selectedWord = null },
        ) {
            LexoWordCard(word = selectedWord!!)
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun WordRow(word: Word, onClick: () -> Unit) {
    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp),
    ) {
        Row(
            Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(Modifier.weight(1f)) {
                Text(word.text, style = MaterialTheme.typography.titleMedium)
                val sub =
                    buildString {
                        if (!word.transcription.isNullOrBlank()) append(word.transcription)
                        if (isNotEmpty()) append(" • ")
                        append(word.translation)
                    }
                Text(sub, style = MaterialTheme.typography.bodyMedium, color = TextPrimary.copy(0.7f))
            }
        }
    }
}
