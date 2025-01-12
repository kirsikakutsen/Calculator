package com.example.calculator.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.calculator.R
import com.example.calculator.ui.theme.CalculatorTheme


@Composable
fun CalculatorScreen(
    modifier: Modifier = Modifier,
    onThemeSwitched: () -> Unit,
    isDarkTheme: Boolean,
    viewModel: CalculatorViewModel = viewModel()
) {
    val equation = viewModel.equation.value
    val result = viewModel.resultState.collectAsState(ResultState()).value
    CalculatorScreenContent(
        modifier = modifier,
        onThemeSwitched = onThemeSwitched,
        isDarkTheme = isDarkTheme,
        onDeleteClicked = { viewModel.deleteLastCharacter() },
        onClearClicked = { viewModel.clear() },
        onActionClicked = { action: String ->
            viewModel.updateEquation(action)
        },
        onResultClicked = { viewModel.calculateResult() },
        result = result,
        equation = equation
    )
}

@Composable
fun CalculatorScreenContent(
    modifier: Modifier = Modifier,
    equation: String,
    result: ResultState,
    onThemeSwitched: () -> Unit,
    isDarkTheme: Boolean,
    onActionClicked: (action: String) -> Unit,
    onClearClicked: () -> Unit,
    onResultClicked: () -> Unit,
    onDeleteClicked: () -> Unit
) {
    Column(modifier = modifier) {
        DisplaySection(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            equation = equation,
            result = result
        )
        ButtonsSection(
            modifier = Modifier,
            onThemeSwitched = onThemeSwitched,
            isDarkTheme = isDarkTheme,
            onActionClicked = onActionClicked,
            onClearClicked = onClearClicked,
            onResultClicked = onResultClicked,
            onDeleteClicked = onDeleteClicked
        )
    }
}

@Composable
fun DisplaySection(
    modifier: Modifier = Modifier,
    equation: String,
    result: ResultState
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Bottom
    ) {
        val textWeight = if (result.data == null) FontWeight.Bold else FontWeight.Normal
        val textSize = if (result.data == null) 32.sp else 24.sp
        val scrollState = rememberScrollState()

        LaunchedEffect(equation) {
            scrollState.scrollTo(scrollState.maxValue)
        }

        val equationStartPadding = remember { mutableStateOf(40.dp) }

        LaunchedEffect(scrollState.value) {

            if (scrollState.value == 0) {
                equationStartPadding.value = 15.dp
            } else {
                equationStartPadding.value = 0.dp
            }
        }

        Text(
            text = equation,
            maxLines = 1,
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = textSize,
            fontWeight = textWeight,
            modifier = Modifier
                .padding(
                    start = equationStartPadding.value,
                    end = 40.dp,
                    top = 20.dp,
                    bottom = 10.dp
                )
                .horizontalScroll(scrollState)
        )
        val resultText = if (result.isError) stringResource(R.string.error_message) else result.data
        Text(
            text = resultText ?: "",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(
                    start = 10.dp,
                    end = 40.dp,
                    top = 10.dp,
                    bottom = 40.dp
                )
                .horizontalScroll(rememberScrollState())
        )
    }
}

@Composable
fun ButtonsSection(
    modifier: Modifier = Modifier,
    onThemeSwitched: () -> Unit,
    isDarkTheme: Boolean,
    onActionClicked: (action: String) -> Unit,
    onClearClicked: () -> Unit,
    onResultClicked: () -> Unit,
    onDeleteClicked: () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(550.dp),
            shape = RoundedCornerShape(
                topStart = 30.dp,
                topEnd = 30.dp,
                bottomEnd = 0.dp,
                bottomStart = 0.dp
            ),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(4)
            ) {
                item { ChangeThemeButton(
                    modifier = Modifier.size(64.dp),
                    onClick = { onThemeSwitched() },
                    isDarkTheme = isDarkTheme
                ) }
                item { IconButton(
                    painter = painterResource(id = R.drawable.multiplication_icon),
                    contentDescription = "multiply",
                    onClick = { onActionClicked(" x ") },
                    modifier = Modifier
                        .padding(10.dp)
                        .size(64.dp)
                ) }
                item { IconButton(
                    painter = painterResource(id = R.drawable.division_icon),
                    contentDescription = "divide",
                    onClick = { onActionClicked(" / ") },
                    modifier = Modifier
                        .padding(10.dp)
                        .size(64.dp)
                ) }
                item { IconButton(
                    painter = painterResource(id = R.drawable.backspace_icon),
                    contentDescription = "delete",
                    onClick = { onDeleteClicked() },
                    modifier = Modifier
                        .padding(10.dp)
                        .size(64.dp)
                ) }

                item { ValueButton(
                    text = "7",
                    modifier = Modifier
                        .padding(10.dp)
                        .size(64.dp),
                    onClick = { onActionClicked("7") },
                    color = MaterialTheme.colorScheme.onPrimary
                ) }
                item { ValueButton(
                    text = "8",
                    modifier = Modifier
                        .padding(10.dp)
                        .size(64.dp),
                    onClick = { onActionClicked("8") },
                    color = MaterialTheme.colorScheme.onPrimary
                ) }
                item { ValueButton(
                    text = "9",
                    modifier = Modifier
                        .padding(10.dp)
                        .size(64.dp),
                    onClick = { onActionClicked("9") },
                    color = MaterialTheme.colorScheme.onPrimary
                ) }
                item { IconButton(
                    painter = painterResource(id = R.drawable.subtraction_icon),
                    contentDescription = "minus",
                    onClick = { onActionClicked(" - ") },
                    modifier = Modifier
                        .padding(10.dp)
                        .size(64.dp)
                ) }

                item { ValueButton(
                    text = "4",
                    modifier = Modifier
                        .padding(10.dp)
                        .size(64.dp),
                    onClick = { onActionClicked("4") },
                    color = MaterialTheme.colorScheme.onPrimary
                ) }
                item { ValueButton(
                    text = "5",
                    modifier = Modifier
                        .padding(10.dp)
                        .size(64.dp),
                    onClick = { onActionClicked("5") },
                    color = MaterialTheme.colorScheme.onPrimary
                ) }
                item { ValueButton(
                    text = "6",
                    modifier = Modifier
                        .padding(10.dp)
                        .size(64.dp),
                    onClick = { onActionClicked("6") },
                    color = MaterialTheme.colorScheme.onPrimary
                ) }
                item { IconButton(
                    modifier = Modifier
                        .padding(10.dp)
                        .size(64.dp),
                    painter = painterResource(id = R.drawable.addition_icon),
                    contentDescription = "plus",
                    onClick = { onActionClicked(" + ") },
                ) }

                item { ValueButton(
                    text = "1",
                    modifier = Modifier
                        .padding(10.dp)
                        .size(64.dp),
                    onClick = { onActionClicked("1") },
                    color = MaterialTheme.colorScheme.onPrimary
                ) }
                item { ValueButton(
                    text = "2",
                    modifier = Modifier
                        .padding(10.dp)
                        .size(64.dp),
                    onClick = { onActionClicked("2") },
                    color = MaterialTheme.colorScheme.onPrimary
                ) }
                item { ValueButton(
                    text = "3",
                    modifier = Modifier
                        .padding(10.dp)
                        .size(64.dp),
                    onClick = { onActionClicked("3") },
                    color = MaterialTheme.colorScheme.onPrimary
                ) }
                item { EqualsButton(
                    modifier = Modifier.padding(10.dp),
                    onClick = { onResultClicked() }
                ) }

                item { DeleteAllButton(
                    onClick = { onClearClicked() },
                    modifier = Modifier.size(64.dp)
                ) }
                item { ValueButton(
                    text = "0",
                    modifier = Modifier
                        .padding(10.dp)
                        .size(64.dp),
                    onClick = { onActionClicked("0") },
                    color = MaterialTheme.colorScheme.onPrimary
                ) }
                item { ValueButton(
                    text = ".",
                    modifier = Modifier
                        .padding(10.dp)
                        .size(64.dp),
                    onClick = { onActionClicked(".") },
                    color = MaterialTheme.colorScheme.onPrimary
                ) }
            }
        }
    }
}


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
fun IconButton(
    painter: Painter,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier
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
                contentDescription = contentDescription,
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
                )
                .padding(top = 51.dp, bottom = 50.dp, start = 0.dp, end = 0.dp)
                .size(64.dp)
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

@Preview(
    showSystemUi = true,
    showBackground = true,
    device = Devices.PIXEL_7
)
@Composable
fun CalculatorScreenPreview(modifier: Modifier = Modifier) {
    CalculatorTheme {
        CalculatorScreen(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            onThemeSwitched = {},
            isDarkTheme = false
        )
    }
}