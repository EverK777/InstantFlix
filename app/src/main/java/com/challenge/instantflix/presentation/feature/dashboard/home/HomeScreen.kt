package com.challenge.instantflix.presentation.feature.dashboard.home

import androidx.compose.runtime.Composable
import androidx.paging.compose.LazyPagingItems
import com.challenge.instantflix.core.data.model.MovieTvEntity

@Composable
fun HomeScreen(
    trendingMoviesAndTvShows: LazyPagingItems<MovieTvEntity>,
) {
}
