package com.example.calculator.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import net.objecthunter.exp4j.ExpressionBuilder

class CalculatorViewModel : ViewModel() {

    val equation = mutableStateOf("")

    private val _resultState = MutableSharedFlow<ResultState>()
    val resultState = _resultState.asSharedFlow()

    fun updateEquation(newInput: String) {
        equation.value += newInput
    }

    fun calculateResult() {
        viewModelScope.launch {
            _resultState.emit(ResultState(isLoading = true))
            runCatching {
                val formattedEquation = equation.value.replace("x", "*")
                val expression = ExpressionBuilder(formattedEquation).build()
                expression.evaluate()
            }.onSuccess { evalResult ->
                _resultState.emit(ResultState(data = String.format("%,.2f", evalResult)))
            }.onFailure {
                _resultState.emit(ResultState(isError = true))
            }
        }
    }

    fun clear() {
        equation.value = ""
        viewModelScope.launch {
            _resultState.emit(ResultState(data = null))
        }
    }

    fun deleteLastCharacter() {
        if (equation.value.isNotEmpty()) {
            if (equation.value.endsWith(" ")) {
                equation.value = equation.value.dropLast(3)
            } else {
                equation.value = equation.value.dropLast(1)
            }
        }
    }
}
