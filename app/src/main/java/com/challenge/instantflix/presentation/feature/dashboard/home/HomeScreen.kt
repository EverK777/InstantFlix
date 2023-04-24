package com.challenge.instantflix.presentation.feature.dashboard.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.paging.compose.LazyPagingItems
import com.challenge.instantflix.core.data.model.MovieTvEntity
import com.challenge.instantflix.presentation.ui.composables.BottomGradientComposable
import com.challenge.instantflix.presentation.ui.composables.TopGradientComposable
import com.challenge.instantflix.presentation.ui.composables.TrendingPagerComposable

@Composable
fun HomeScreen(
    trendingMoviesAndTvShows: LazyPagingItems<MovieTvEntity>,
) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize(),
    ) {
        val (topBar, content) = createRefs()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(topBar) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
        ) {
            TopGradientComposable(
                modifier = Modifier
                    .height(80.dp)
                    .fillMaxWidth(),
            )
        }

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .constrainAs(content) {
                    top.linkTo(topBar.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            content = {
                Box(modifier = Modifier.padding(it)) {
                    TrendingPagerComposable(trendingMoviesAndTvShows = trendingMoviesAndTvShows)
                    BottomGradientComposable(modifier = Modifier.fillMaxSize())
                }
            },
            snackbarHost = {
            },
        )
    }
}
