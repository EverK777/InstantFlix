package com.challenge.instantflix.presentation.feature.dashboard.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.paging.compose.LazyPagingItems
import com.challenge.instantflix.R
import com.challenge.instantflix.core.data.model.MovieTvEntity
import com.challenge.instantflix.presentation.ui.composables.ListPagerComposable
import com.challenge.instantflix.presentation.ui.composables.TopGradientComposable
import com.challenge.instantflix.presentation.ui.composables.TrendingComposable

@Composable
fun HomeScreen(
    movieTvEntity: () -> MovieTvEntity?,
    popularMovies: LazyPagingItems<MovieTvEntity>,
    popularTvShows: LazyPagingItems<MovieTvEntity>,
    topRatedMovies: LazyPagingItems<MovieTvEntity>,
    topRatedTvShows: LazyPagingItems<MovieTvEntity>,
) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize(),
    ) {
        val (topBar, content) = createRefs()

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .constrainAs(content) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            content = {
                LazyColumn(modifier = Modifier.padding(it)) {
                    item {
                        TrendingComposable(movieTvEntity)
                    }
                    item {
                        ListPagerComposable(
                            title = stringResource(R.string.popular_movies),
                            pagingItems = popularMovies,
                        )
                    }

                    item {
                        ListPagerComposable(
                            title = stringResource(R.string.popular_tv_shows),
                            pagingItems = popularTvShows,
                        )
                    }

                    item {
                        ListPagerComposable(
                            title = stringResource(R.string.top_rated_movie),
                            pagingItems = topRatedMovies,
                        )
                    }

                    item {
                        ListPagerComposable(
                            title = stringResource(R.string.top_rated_tv_shows),
                            pagingItems = topRatedTvShows,
                        )
                    }
                }
            },
            snackbarHost = {
            },
        )

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
                    .height(120.dp)
                    .fillMaxWidth(),
            )
        }
    }
}
