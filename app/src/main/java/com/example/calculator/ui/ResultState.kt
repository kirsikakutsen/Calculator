package com.example.calculator.ui

data class ResultState(
    val isError: Boolean = false,
    val data: String? = null,
    val isLoading: Boolean = false
)