package com.heuron.heuronproject.presentation.util

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType

// 텍스트필드 커스텀(특정조건에 따른 숫자 및 문자만 입력되게 처리)
@Composable
fun CustomTextField(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit
) {
    val isNumberField = label in listOf("ID", "Width", "Height")

    OutlinedTextField(
        value = value,
        onValueChange = {
            val filtered = if (isNumberField) {
                it.filter { ch -> ch.isDigit() }
            } else {
                it.filter { ch -> ch.isLetter() || ch == ' ' }
            }
            onValueChange(filtered)
        },
        label = { Text(label) },
        modifier = modifier,
        keyboardOptions = KeyboardOptions(
            keyboardType = if (isNumberField) KeyboardType.Number else KeyboardType.Text
        ),
        singleLine = true
    )
}
