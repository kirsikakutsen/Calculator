package com.example.calculator.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    background = ThemeColors.Dark.background,
    primaryContainer = ThemeColors.Dark.surface,
    primary = ThemeColors.Dark.primary,
    onPrimary = ThemeColors.Dark.text
)

private val LightColorScheme = lightColorScheme(
    background = ThemeColors.Light.background,
    primaryContainer = ThemeColors.Light.surface,
    primary = ThemeColors.Light.primary,
    onPrimary = ThemeColors.Light.text
)




@Composable
fun CalculatorTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}