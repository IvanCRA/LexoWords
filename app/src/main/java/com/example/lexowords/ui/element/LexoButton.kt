package com.example.lexowords.ui.element

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lexowords.ui.theme.PurplePrimary
import com.example.lexowords.ui.theme.TextPrimary

@Composable
fun LexoButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        modifier =
            modifier
                .width(200.dp)
                .height(57.dp),
        // .fillMaxWidth(),
        shape = RoundedCornerShape(26.dp),
        colors =
            ButtonDefaults.buttonColors(
                containerColor = PurplePrimary,
                contentColor = TextPrimary,
            ),
        elevation =
            ButtonDefaults.buttonElevation(
                defaultElevation = 4.dp,
                pressedElevation = 8.dp,
            ),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MyPreviewLexoButton() {
    LexoButton(onClick = {}, text = "Govno")
}
