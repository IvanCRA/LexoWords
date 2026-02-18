package com.example.lexowords.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.example.lexowords.R
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography =
    Typography(
        bodyLarge =
            TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.5.sp,
            ),
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
     */
    )
val SourceSansPro =
    FontFamily(
        Font(R.font.source_sans_pro_regular, weight = FontWeight.Normal),
        Font(R.font.source_sans_pro_bold, weight = FontWeight.Bold),
        Font(R.font.source_sans_pro_extralight, weight = FontWeight.ExtraLight),
        Font(R.font.source_sans_pro_black, weight = FontWeight.Black),
        Font(R.font.source_sans_pro_italic, weight = FontWeight.Normal, FontStyle.Italic),
        Font(R.font.source_sans_pro_black_italic, weight = FontWeight.Black, FontStyle.Italic),
        Font(R.font.source_sans_pro_bold_italic, weight = FontWeight.Bold, FontStyle.Italic),
        Font(R.font.source_sans_pro_light_italic, weight = FontWeight.Light, FontStyle.Italic),
        Font(R.font.source_sans_pro_semi_bold, weight = FontWeight.SemiBold),
        Font(R.font.source_sans_pro_semi_bold_italic, weight = FontWeight.SemiBold, FontStyle.Italic),
    )

val LexoTypography =
    Typography(
        displayLarge =
            TextStyle(
                fontFamily = SourceSansPro,
                fontSize = 57.sp,
                lineHeight = 64.sp,
            ),
        displayMedium =
            TextStyle(
                fontFamily = SourceSansPro,
                fontSize = 45.sp,
                lineHeight = 52.sp,
            ),
        displaySmall =
            TextStyle(
                fontFamily = SourceSansPro,
                fontSize = 36.sp,
                lineHeight = 44.sp,
            ),
        headlineLarge =
            TextStyle(
                fontFamily = SourceSansPro,
                fontSize = 32.sp,
                lineHeight = 40.sp,
            ),
        headlineMedium =
            TextStyle(
                fontFamily = SourceSansPro,
                fontSize = 28.sp,
                lineHeight = 36.sp,
            ),
        headlineSmall =
            TextStyle(
                fontFamily = SourceSansPro,
                fontSize = 24.sp,
                lineHeight = 32.sp,
            ),
        titleLarge =
            TextStyle(
                fontFamily = SourceSansPro,
                fontSize = 22.sp,
                lineHeight = 28.sp,
            ),
        titleMedium =
            TextStyle(
                fontFamily = SourceSansPro,
                fontSize = 16.sp,
                lineHeight = 24.sp,
            ),
        titleSmall =
            TextStyle(
                fontFamily = SourceSansPro,
                fontSize = 14.sp,
                lineHeight = 20.sp,
            ),
        bodyLarge =
            TextStyle(
                fontFamily = SourceSansPro,
                fontSize = 16.sp,
                lineHeight = 24.sp,
            ),
        bodyMedium =
            TextStyle(
                fontFamily = SourceSansPro,
                fontSize = 14.sp,
                lineHeight = 20.sp,
            ),
        bodySmall =
            TextStyle(
                fontFamily = SourceSansPro,
                fontSize = 12.sp,
                lineHeight = 16.sp,
            ),
        labelLarge =
            TextStyle(
                fontFamily = SourceSansPro,
                fontSize = 14.sp,
                lineHeight = 20.sp,
            ),
        labelMedium =
            TextStyle(
                fontFamily = SourceSansPro,
                fontSize = 12.sp,
                lineHeight = 16.sp,
            ),
        labelSmall =
            TextStyle(
                fontFamily = SourceSansPro,
                fontSize = 11.sp,
                lineHeight = 16.sp,
            ),
    )
