package com.rutaya.app.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val Colors = lightColorScheme(
    primary = Color(0xFF176B61),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFB9F1E5),
    onPrimaryContainer = Color(0xFF00201C),
    secondary = Color(0xFFE99B33),
    background = Color(0xFFF7FAF8),
    surface = Color.White,
    onSurface = Color(0xFF17201E),
    error = Color(0xFFBA1A1A)
)

@Composable
fun RutaYaTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = Colors, content = content)
}
