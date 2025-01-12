package com.example.calculator.ui.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculator.R


@Composable
fun DeleteAllButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        onClick = onClick
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "AC",
                style = TextStyle(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFFED0E98),
                            Color(0xFFFE5A2D)
                        )
                    ),
                    fontWeight = FontWeight.Normal,
                    fontSize = 25.sp
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ChangeThemeButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    isDarkTheme: Boolean,
) {
    OutlinedButton(
        modifier = modifier,
        contentPadding = PaddingValues(),
        onClick = onClick,
        border = BorderStroke(
            width = 3.dp,
            color = MaterialTheme.colorScheme.primary
        )
    ) {
        val imageRes =
            if (isDarkTheme) R.drawable.sun_icon else R.drawable.moon_icon
        val imageSize = if (isDarkTheme) 32.dp else 24.dp
        Image(
            painter = painterResource(imageRes),
            contentDescription = "theme switch",
            modifier = Modifier.size(imageSize),
            contentScale = ContentScale.FillBounds
        )
    }
}

@Composable
fun ActionButton(
    modifier: Modifier,
    painter: Painter,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier,
        shape = CircleShape,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
        onClick = onClick
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painter,
                tint = MaterialTheme.colorScheme.onPrimary,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun ValueButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    color: Color
) {
    Card(
        modifier = modifier,
        onClick = onClick,
        shape = CircleShape,
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                modifier = Modifier.align(alignment = Alignment.Center),
                text = text,
                fontSize = 25.sp,
                color = color
            )
        }
    }
}

@Composable
fun EqualsButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 30.dp),
        shape = RoundedCornerShape(35.dp),
        modifier = modifier,
        onClick = onClick
    ) {
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFFED0E98),
                            Color(0xFFFE5A2D)
                        )
                    )
                ).fillMaxSize()
        )
        {
            Text(
                modifier = Modifier.align(alignment = Alignment.Center),
                text = "=",
                color = Color.White,
                fontSize = 55.sp,
                fontWeight = FontWeight.Light
            )
        }
    }
}