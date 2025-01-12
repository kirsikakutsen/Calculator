package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.calculator.ui.CalculatorScreen
import com.example.calculator.ui.theme.CalculatorTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        val sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        setContent {
            val isSystemDark = isSystemInDarkTheme()

            var isDarkThemeState by remember { mutableStateOf(isSystemDark) }
            val isDarkThemeFromStorage = sharedPreferences.getString("DarkTheme", null)

            LaunchedEffect(isSystemDark) {
                isDarkThemeState = isDarkThemeFromStorage?.toBoolean() ?: isSystemDark
            }

            CalculatorTheme(
                darkTheme = isDarkThemeState
            ) {
                CalculatorScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background),
                    onThemeSwitched = {
                        editor.putString("DarkTheme", (!isDarkThemeState).toString())
                        editor.apply()
                        isDarkThemeState = !isDarkThemeState
                    },
                    isDarkTheme = isDarkThemeState
                )
            }
        }
    }
}


