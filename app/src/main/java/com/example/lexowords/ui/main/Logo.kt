package com.example.lexowords.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lexowords.R
import com.example.lexowords.ui.theme.PurplePrimary

@Composable
fun Logo() {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(R.drawable.logo_1),
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                colorFilter = ColorFilter.tint(Color(0xFF9A3EFF)),
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "Lexo\nWords",
                style =
                    MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 35.sp,
                        color = PurplePrimary,
                    ),
            )
        }

        Text(
            text = "Learn. Remember. Repeat.",
            style =
                MaterialTheme.typography.bodyLarge.copy(
                    color = PurplePrimary,
                ),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MyPreview() {
    Logo()
}
