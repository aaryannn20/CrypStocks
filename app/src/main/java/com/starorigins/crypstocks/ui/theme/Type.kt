package com.starorigins.crypstocks.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.starorigins.crypstocks.R

val Inter = FontFamily(
    Font(R.font.inter_thin, FontWeight.W100),
    Font(R.font.inter_extralight, FontWeight.W200),
    Font(R.font.inter_light, FontWeight.W300),
    Font(R.font.inter_regular, FontWeight.W400),
    Font(R.font.inter_medium, FontWeight.W500),
    Font(R.font.inter_semibold, FontWeight.W600),
    Font(R.font.inter_bold, FontWeight.W700),
    Font(R.font.inter_extrabold, FontWeight.W800),
    Font(R.font.inter_black, FontWeight.W900)
)

val typography = Typography(
    headlineLarge = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.W600,
        fontSize = 38.sp
    ),
    titleLarge = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.W800,
        fontSize = 24.sp
    ),
    titleMedium = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.W600,
        fontSize = 20.sp
    ),
    titleSmall = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.W400,
        fontSize = 20.sp
    ),
    labelLarge = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    labelMedium = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.W300,
        letterSpacing = 1.25.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.W400,
        fontSize = 16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.W400,
        fontSize = 14.sp
    ),
    displaySmall = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp,
        letterSpacing = 1.25.sp
    ),
     labelSmall = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.W400,
        fontSize = 12.sp
    )
)

