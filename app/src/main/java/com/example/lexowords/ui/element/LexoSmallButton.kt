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
fun LexoSmallButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        modifier =
            modifier
                .width(125.dp)
                .height(50.dp),
        shape = RoundedCornerShape(26.dp),
        colors =
            ButtonDefaults.buttonColors(
                contentColor = TextPrimary,
                containerColor = PurplePrimary,
            ),
        elevation = ButtonDefaults.buttonElevation(4.dp),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MyPreview() {
    LexoSmallButton(onClick = {}, text = "Govno")
}
