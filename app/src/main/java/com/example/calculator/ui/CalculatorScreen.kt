package com.example.calculator.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.calculator.R
import com.example.calculator.ui.buttons.ActionButton
import com.example.calculator.ui.buttons.Button
import com.example.calculator.ui.buttons.ButtonType
import com.example.calculator.ui.buttons.ChangeThemeButton
import com.example.calculator.ui.buttons.DeleteAllButton
import com.example.calculator.ui.buttons.EqualsButton
import com.example.calculator.ui.buttons.ValueButton
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
        onClearClicked = { viewModel.clear() },
        onActionClicked = { action: String ->
            if (action == "delete") {
                viewModel.deleteLastCharacter()
            } else {
                viewModel.updateEquation(action)
            }
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
            Row {
                LazyHorizontalGrid(
                    modifier = Modifier
                        .weight(1f)
                        .padding(20.dp),
                    rows = GridCells.Fixed(5),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    items(
                        items = Button.generateButtonList(),
                        span = { item ->
                            if (item.type == ButtonType.EQUALS) {
                                GridItemSpan(2)
                            } else {
                                GridItemSpan(1)
                            }
                        }
                    ) { item ->
                        when (item.type) {
                            ButtonType.VALUE -> {
                                ValueButton(
                                    text = item.value.toString(),
                                    modifier = Modifier
                                        .size(64.dp)
                                        .aspectRatio(1f),
                                    onClick = { onActionClicked(item.value.toString()) },
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                            }

                            ButtonType.DELETE_ALL -> {
                                DeleteAllButton(
                                    modifier = Modifier
                                        .size(64.dp)
                                        .aspectRatio(1f),
                                    onClick = onClearClicked
                                )
                            }

                            ButtonType.CHANGE_THEME -> {
                                ChangeThemeButton(
                                    modifier = Modifier
                                        .size(64.dp)
                                        .aspectRatio(1f),
                                    onClick = onThemeSwitched,
                                    isDarkTheme = isDarkTheme
                                )
                            }

                            ButtonType.EQUALS -> {
                                EqualsButton(
                                    modifier = Modifier
                                        .padding(vertical = 10.dp)
                                        .width(64.dp),
                                    onClick = onResultClicked
                                )
                            }

                            ButtonType.ACTION -> {
                                item.value.toString().toIntOrNull()?.let {
                                    if (item.action == null) {
                                        return@items
                                    }
                                    ActionButton(
                                        modifier = Modifier
                                            .size(64.dp)
                                            .aspectRatio(1f),
                                        painter = painterResource(it),
                                        onClick = { onActionClicked(item.action) }
                                    )
                                }
                            }
                        }
                    }
                }
            }
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