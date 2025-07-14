package com.example.lexowords.ui.words

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lexowords.data.repository.FakeWordRepository

@Composable
fun FlashCardScreen() {
    val vm = remember { WordsViewModel(FakeWordRepository()) }
    val word = vm.currentWord

    if (word == null) {
        Text("Все слова изучены", modifier = Modifier.fillMaxSize().wrapContentSize())
    } else {
        Card(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .clickable { vm.onCardClick() },
        ) {
            Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                Text(
                    text = if (vm.showTranslation) word.translation else word.text,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.align(Alignment.Center),
                )
                if (vm.showTranslation) {
                    Row(
                        modifier =
                            Modifier
                                .align(Alignment.BottomCenter)
                                .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                    ) {
                        Button(onClick = { vm.onAnswer(false) }) { Text("Нет") }
                        Button(onClick = { vm.onAnswer(true) }) { Text("Да") }
                    }
                }
            }
        }
    }
}
