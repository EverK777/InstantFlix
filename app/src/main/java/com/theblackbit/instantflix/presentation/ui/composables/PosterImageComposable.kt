package com.theblackbit.instantflix.presentation.ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
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
    )
}
