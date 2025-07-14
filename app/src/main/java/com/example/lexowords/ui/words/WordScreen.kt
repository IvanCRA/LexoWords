package com.example.lexowords.ui.words

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lexowords.data.repository.FakeWordRepository
import com.example.lexowords.domain.model.Word

@Composable
fun WordScreen() {
    val viewModel = remember { WordsViewModel(FakeWordRepository()) }
    WordListScreen(viewModel = viewModel)
}

@Composable
fun WordListScreen(viewModel: WordsViewModel) {
    val words = viewModel.words

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(items = words, key = { it.id }) { word ->
            WordItem(word = word, onFavoriteClick = {
                viewModel.onFavoriteClick(word.id)
            })
        }
    }
}

@Composable
fun WordItem(word: Word, onFavoriteClick: () -> Unit) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = word.text, style = MaterialTheme.typography.titleMedium)
            Text(text = word.translation, style = MaterialTheme.typography.bodySmall)
        }
        IconButton(onClick = onFavoriteClick) {
            Icon(
                imageVector = if (word.isFavorite) Icons.Default.Star else Icons.Default.Check,
                contentDescription = "Favorite",
            )
        }
    }
}
