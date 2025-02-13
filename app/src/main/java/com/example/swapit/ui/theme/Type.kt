package com.example.swapit.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.swapit.R

val pretendard =
    FontFamily(
        Font(R.font.pretendard_bold, FontWeight.Bold),
        Font(R.font.pretendard_regular, FontWeight.Normal),
        Font(R.font.pretendard_light, FontWeight.Light),
    )

val Typography =
    Typography(
        headlineLarge =
            TextStyle(
                fontFamily = pretendard,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = (-0.25).sp,
                lineHeight = 54.sp,
            ),
        headlineMedium =
            TextStyle(
                fontFamily = pretendard,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = (-0.25).sp,
                lineHeight = 36.sp,
            ),
        headlineSmall =
            TextStyle(
                fontFamily = pretendard,
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = (-0.25).sp,
                lineHeight = 31.5.sp,
            ),
        titleLarge =
            TextStyle(
                fontFamily = pretendard,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = (-0.25).sp,
                lineHeight = 26.1.sp,
            ),
        titleMedium =
            TextStyle(
                fontFamily = pretendard,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = (-0.25).sp,
                lineHeight = 24.sp,
            ),
        titleSmall =
            TextStyle(
                fontFamily = pretendard,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = (-0.25).sp,
                lineHeight = 18.2.sp,
            ),
        bodyLarge =
            TextStyle(
                fontFamily = pretendard,
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                letterSpacing = (-0.25).sp,
                lineHeight = 26.1.sp,
            ),
        bodyMedium =
            TextStyle(
                fontFamily = pretendard,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                letterSpacing = (-0.25).sp,
                lineHeight = 22.4.sp,
            ),
        bodySmall =
            TextStyle(
                fontFamily = pretendard,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                letterSpacing = (-0.25).sp,
                lineHeight = 18.2.sp,
            ),
        labelLarge =
            TextStyle(
                fontFamily = pretendard,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                letterSpacing = (-0.25).sp,
                lineHeight = 15.6.sp,
            ),
        labelMedium =
            TextStyle(
                fontFamily = pretendard,
                fontSize = 11.sp,
                fontWeight = FontWeight.Light,
                letterSpacing = (-0.25).sp,
                lineHeight = 15.95.sp,
            ),
        labelSmall =
            TextStyle(
                fontFamily = pretendard,
                fontSize = 10.sp,
                fontWeight = FontWeight.Light,
                letterSpacing = (-0.25).sp,
                lineHeight = 14.sp,
            ),
    )
