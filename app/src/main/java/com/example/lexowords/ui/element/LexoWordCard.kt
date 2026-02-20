package com.example.lexowords.ui.element

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lexowords.R
import com.example.lexowords.data.model.WordStudyState
import com.example.lexowords.domain.model.Word
import com.example.lexowords.ui.theme.SecondaryBackgroundDark
import com.example.lexowords.ui.theme.TextPrimary

@Composable
fun LexoWordCard(word: Word) {
    var revealed by remember(word.id) { mutableStateOf(false) }

    val context = LocalContext.current
    val ttsManager =
        remember {
            com.example.lexowords.ui.audio.TtsManager(context)
        }

    DisposableEffect(Unit) {
        onDispose { ttsManager.shutdown() }
    }

    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .height(240.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = SecondaryBackgroundDark),
        elevation = CardDefaults.cardElevation(6.dp),
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(24.dp),
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = word.text,
                    style =
                        MaterialTheme.typography.headlineLarge.copy(
                            fontSize = 50.sp,
                            fontWeight = FontWeight.Bold,
                        ),
                    color = TextPrimary,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = word.transcription.orEmpty(),
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 30.sp),
                    color = TextPrimary.copy(0.8f),
                )
                Spacer(Modifier.height(8.dp))

                if (revealed) {
                    Text(
                        text = word.translation,
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 35.sp),
                        color = TextPrimary.copy(alpha = 0.7f),
                    )
                } else {
                    Text(
                        text = "Нажми, чтобы увидеть перевод",
                        modifier = Modifier.clickable { revealed = true },
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 20.sp),
                        color = TextPrimary.copy(alpha = 0.4f),
                    )
                }
            }

            IconButton(
                onClick = { ttsManager.speak(word.text) },
                modifier =
                    Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = 6.dp, y = (-6).dp)
                        .size(36.dp),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.round_volume_down_24),
                    contentDescription = "Произнести слово",
                    tint = TextPrimary.copy(alpha = 0.85f),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyPreview_1() {
    var word =
        Word(
            id = 1,
            text = "Serendipity",
            translation = "Случайная удача",
            transcription = "[sɛrənˈdɪpɪti]",
            isFavorite = false,
            addedAt = 1753435063409,
            learned = false,
            nextReviewAt = 1753435063410,
            repetitions = 0,
            interval = 1,
            easeFactor = 2.5F,
            studyState = WordStudyState.TO_REVIEW,
        )
    LexoWordCard(word)
}
