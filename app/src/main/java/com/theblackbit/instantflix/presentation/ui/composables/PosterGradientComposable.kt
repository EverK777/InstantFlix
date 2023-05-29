package com.theblackbit.instantflix.presentation.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PosterGradientComposable(
    imagePoster: () -> String,
    screenHeight: () -> Double,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .wrapContentSize()
            .fillMaxWidth()
            .height(screenHeight.invoke().dp),
    ) {
        PosterImageComposable(
            posterPath = imagePoster.invoke(),
            Modifier.fillMaxSize(),
        )
        BottomGradientComposable(
            modifier = Modifier
                .fillMaxWidth()
                .height(screenHeight.invoke().dp),
        )
    }
}
