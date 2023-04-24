package com.challenge.instantflix.presentation.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import com.challenge.instantflix.presentation.ui.theme.InstantflixTheme

@Composable
fun TopGradientComposable(
    modifier: Modifier = Modifier,
) {
    val gradientAppBar = arrayOf(1f, 0.85f, 0.72f, 0.58f, 0.05f, 0f)

    Box(
        modifier = modifier
            .background(
                Brush.verticalGradient(
                    colorStops = arrayOf(
                        0.0f to MaterialTheme.colorScheme.background.copy(alpha = gradientAppBar[0]),
                        0.25f to MaterialTheme.colorScheme.background.copy(alpha = gradientAppBar[1]),
                        0.30f to MaterialTheme.colorScheme.background.copy(alpha = gradientAppBar[2]),
                        0.40f to MaterialTheme.colorScheme.background.copy(alpha = gradientAppBar[3]),
                        0.50f to MaterialTheme.colorScheme.background.copy(alpha = gradientAppBar[4]),
                        0.60f to MaterialTheme.colorScheme.background.copy(alpha = gradientAppBar[5]),
                    ),
                ),
            ),
    )
}

@Preview
@Composable
fun PreviewTopGradientComposable() {
    InstantflixTheme {
        TopGradientComposable()
    }
}
