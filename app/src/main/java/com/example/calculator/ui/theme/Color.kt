package com.example.calculator.ui.theme

import androidx.compose.ui.graphics.Color

val DarkThemeBackground = Color(0xFF212327)
val LightThemeBackground = Color.White
val DarkThemeCard = Color(0xFF27292E)
val LightThemeCard = Color(0xFFF5F5F5)
val DarkThemeButton = Color(0xFF323438)
val LightThemeButton = Color(0xFFE9E9E9)
val DarkThemeText = Color.White
val LightThemeText = Color(0xFF1E1E1E)

sealed class ThemeColors(
    val background: Color,
    val surface: Color,
    val primary: Color,
    val text: Color
)  {
    object Dark: ThemeColors(
        background = DarkThemeBackground,
        surface = DarkThemeCard,
        primary = DarkThemeButton,
        text = DarkThemeText
    )
    object Light: ThemeColors(
        background = LightThemeBackground,
        surface = LightThemeCard,
        primary = LightThemeButton,
        text = LightThemeText
    )
}