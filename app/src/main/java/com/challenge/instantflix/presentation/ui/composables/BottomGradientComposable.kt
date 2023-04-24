package com.challenge.instantflix.presentation.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush

@Composable
fun BottomGradientComposable(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .background(
                Brush.horizontalGradient(
                    colorStops = arrayOf(
                        0.0f to MaterialTheme.colorScheme.background.copy(alpha = 1f),
                        0.25f to MaterialTheme.colorScheme.background.copy(alpha = 0.85f),
                        0.30f to MaterialTheme.colorScheme.background.copy(alpha = 0.75f),
                        0.40f to MaterialTheme.colorScheme.background.copy(alpha = 0.50f),
                        0.50f to MaterialTheme.colorScheme.background.copy(alpha = 0.25f),
                        0.75f to MaterialTheme.colorScheme.background.copy(alpha = 0f),
                    ),
                ),
            ),
    )
}
