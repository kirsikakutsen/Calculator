package com.example.calculator.ui.buttons

import com.example.calculator.R

data class Button(
    val value: Any? = null,
    val type: ButtonType,
    val action: String? = null
) {
    companion object {
        fun generateButtonList() = listOf(
            Button(type = ButtonType.CHANGE_THEME),
            Button(value = "7", type = ButtonType.VALUE),
            Button(value = "4", type = ButtonType.VALUE),
            Button(value = "1", type = ButtonType.VALUE),
            Button(type = ButtonType.DELETE_ALL),
            Button(value = R.drawable.multiplication_icon, type = ButtonType.ACTION, action = "x"),
            Button(value = "8", type = ButtonType.VALUE),
            Button(value = "5", type = ButtonType.VALUE),
            Button(value = "2", type = ButtonType.VALUE),
            Button(value = "0", type = ButtonType.VALUE),
            Button(value = R.drawable.division_icon, type = ButtonType.ACTION, action = "/"),
            Button(value = "9", type = ButtonType.VALUE),
            Button(value = "6", type = ButtonType.VALUE),
            Button(value = "3", type = ButtonType.VALUE),
            Button(value = ".", type = ButtonType.VALUE),
            Button(value = R.drawable.backspace_icon, type = ButtonType.ACTION, action = "delete"),
            Button(value = R.drawable.subtraction_icon, type = ButtonType.ACTION, action = "-"),
            Button(value = R.drawable.addition_icon, type = ButtonType.ACTION, action = "+"),
            Button(type = ButtonType.EQUALS),
        )
    }
}