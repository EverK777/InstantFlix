package com.challenge.instantflix.presentation.ui.composables

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun PosterImageComposable(
    posterPath: String,
    modifier: Modifier = Modifier,
) {
    GlideImage(
        modifier = modifier,
        imageModel = posterPath,
        contentScale = ContentScale.Crop,
        shimmerParams = ShimmerParams(
            baseColor = MaterialTheme.colorScheme.background,
            highlightColor = Color.LightGray,
            durationMillis = 2000,
            dropOff = 0.65f,
            tilt = 20f,
        ),
    )
}
