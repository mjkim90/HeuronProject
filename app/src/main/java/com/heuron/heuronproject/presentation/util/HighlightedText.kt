package com.heuron.heuronproject.presentation.util

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

// 글자 하이라이팅 함수
@Composable
fun HighlightedText(label: String, content: String, query: String) {
    val annotatedString = buildAnnotatedString {
        append("$label: ")
        if (query.isNotBlank() && content.equals(query, ignoreCase = true)) {
            withStyle(style = SpanStyle(background = Color.Green)) {
                append(content)
            }
        } else {
            append(content)
        }
    }
    Text(text = annotatedString)
}