package com.challenge.instantflix.presentation.feature.dashboard.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.challenge.instantflix.R
import com.challenge.instantflix.core.data.model.MovieTvEntity
import com.challenge.instantflix.core.utils.TestTags.MOVIES_CONTAINER_TAG
import com.challenge.instantflix.presentation.ui.composables.ListPagerComposable
import com.challenge.instantflix.presentation.ui.composables.TopGradientComposable
import com.challenge.instantflix.presentation.ui.composables.TrendingComposable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun HomeScreen(
    movieTvEntityFlow: StateFlow<MovieTvEntity?>,
    popularMovies: Flow<PagingData<MovieTvEntity>>,
    popularTvShows: Flow<PagingData<MovieTvEntity>>,
    topRatedMovies: Flow<PagingData<MovieTvEntity>>,
    topRatedTvShows: Flow<PagingData<MovieTvEntity>>,
    onItemClick: (MovieTvEntity) -> Unit,
) {
    val movieTvEntity = movieTvEntityFlow.collectAsStateWithLifecycle()
    val popularMoviesCollected = popularMovies.collectAsLazyPagingItems()
    val popularTvShowsCollected = popularTvShows.collectAsLazyPagingItems()
    val topRatedMoviesCollected = topRatedMovies.collectAsLazyPagingItems()
    val topRatedTvShowsCollected = topRatedTvShows.collectAsLazyPagingItems()

    val listState = rememberLazyListState()
    val configuration = LocalConfiguration.current
    val heightPoster = configuration.screenHeightDp.dp.value * 0.75
    Box {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize(),
        ) {
            val (topBar, content) = createRefs()

            LazyColumn(
                modifier = Modifier.constrainAs(content) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }.fillMaxSize().testTag(MOVIES_CONTAINER_TAG),
                state = listState,
            ) {
                item {
                    Box(modifier = Modifier.height(heightPoster.dp)) {
                        movieTvEntity.value?.let { movieTvEntity ->
                            TrendingComposable({ movieTvEntity }) {
                                onItemClick.invoke(movieTvEntity)
                            }
                        }
                    }
                }
                item {
                    ListPagerComposable(
                        title = stringResource(R.string.popular_movies),
                        pagingItems = popularMoviesCollected,
                    ) { movieTvEntity ->
                        onItemClick.invoke(movieTvEntity)
                    }
                }

                item {
                    ListPagerComposable(
                        title = stringResource(R.string.popular_tv_shows),
                        pagingItems = popularTvShowsCollected,
                    ) { movieTvEntity ->
                        onItemClick.invoke(movieTvEntity)
                    }
                }

                item {
                    ListPagerComposable(
                        title = stringResource(R.string.top_rated_movie),
                        pagingItems = topRatedMoviesCollected,
                    ) { movieTvEntity ->
                        onItemClick.invoke(movieTvEntity)
                    }
                }

                item {
                    ListPagerComposable(
                        title = stringResource(R.string.top_rated_tv_shows),
                        pagingItems = topRatedTvShowsCollected,
                    ) { movieTvEntity ->
                        onItemClick.invoke(movieTvEntity)
                    }
                }
            }

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

        if (movieTvEntity.value == null &&
            popularMoviesCollected.itemSnapshotList.isEmpty() &&
            popularTvShowsCollected.itemSnapshotList.isEmpty() &&
            topRatedMoviesCollected.itemSnapshotList.isEmpty() &&
            topRatedTvShowsCollected.itemSnapshotList.isEmpty()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        }
    }
}
