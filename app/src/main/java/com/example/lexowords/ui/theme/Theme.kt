package com.example.lexowords.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme =
    darkColorScheme(
        primary = Purple80,
        secondary = PurpleGrey80,
        tertiary = Pink80,
    )

private val LexoColorScheme =
    darkColorScheme(
        primary = PurplePrimary,
        secondary = PurpleDark,
        background = BackgroundDark,
        surface = SecondaryBackgroundDark,
        onPrimary = TextPrimary,
        onBackground = TextPrimary,
        onSurface = TextPrimary,
    )
private val LightColorScheme =
    lightColorScheme(
        primary = Purple40,
        secondary = PurpleGrey40,
        tertiary = Pink40,
    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
     */
    )

@Composable
fun LexoWordsTheme(
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = LexoColorScheme,
        typography = LexoTypography,
        content = content,
    )
}
