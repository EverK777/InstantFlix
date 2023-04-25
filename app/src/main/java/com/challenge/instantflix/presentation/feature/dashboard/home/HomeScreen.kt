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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import com.challenge.instantflix.R
import com.challenge.instantflix.core.data.model.MovieTvEntity
import com.challenge.instantflix.core.data.model.formatGenres
import com.challenge.instantflix.core.data.model.getImagePoster
import com.challenge.instantflix.core.utils.emptyStringHandler
import com.challenge.instantflix.presentation.ui.composables.ListPagerComposable
import com.challenge.instantflix.presentation.ui.composables.TopGradientComposable
import com.challenge.instantflix.presentation.ui.composables.TrendingComposable
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun HomeScreen(
    movieTvEntity: () -> MovieTvEntity?,
    popularMovies: LazyPagingItems<MovieTvEntity>,
    popularTvShows: LazyPagingItems<MovieTvEntity>,
    topRatedMovies: LazyPagingItems<MovieTvEntity>,
    topRatedTvShows: LazyPagingItems<MovieTvEntity>,
    navController: NavHostController,
) {
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
                }.fillMaxSize(),
                state = listState,
            ) {
                item {
                    Box(modifier = Modifier.height(heightPoster.dp)) {
                        TrendingComposable(movieTvEntity) {
                            movieTvEntity.invoke()?.let { movieTvEntity ->
                                navigateToDetail(movieTvEntity, navController)
                            }
                        }
                    }
                }
                item {
                    ListPagerComposable(
                        title = stringResource(R.string.popular_movies),
                        pagingItems = popularMovies,
                    ) { movieTvEntity ->
                        navigateToDetail(movieTvEntity, navController)
                    }
                }

                item {
                    ListPagerComposable(
                        title = stringResource(R.string.popular_tv_shows),
                        pagingItems = popularTvShows,
                    ) { movieTvEntity ->
                        navigateToDetail(movieTvEntity, navController)
                    }
                }

                item {
                    ListPagerComposable(
                        title = stringResource(R.string.top_rated_movie),
                        pagingItems = topRatedMovies,
                    ) { movieTvEntity ->
                        navigateToDetail(movieTvEntity, navController)
                    }
                }

                item {
                    ListPagerComposable(
                        title = stringResource(R.string.top_rated_tv_shows),
                        pagingItems = topRatedTvShows,
                    ) { movieTvEntity ->
                        navigateToDetail(movieTvEntity, navController)
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

        if (movieTvEntity.invoke() == null &&
            popularMovies.itemSnapshotList.isEmpty() &&
            popularTvShows.itemSnapshotList.isEmpty() &&
            topRatedMovies.itemSnapshotList.isEmpty() &&
            topRatedTvShows.itemSnapshotList.isEmpty() &&
            topRatedTvShows.itemSnapshotList.isEmpty()
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

private fun navigateToDetail(movieTvEntity: MovieTvEntity, navController: NavHostController) {
    val poster = movieTvEntity.getImagePoster()
    val encodedUrl = URLEncoder.encode(poster, StandardCharsets.UTF_8.toString())
    val type = movieTvEntity.typeRequest.type
    val name = movieTvEntity.title.emptyStringHandler()
    val overview = movieTvEntity.overview.emptyStringHandler()
    val genres = movieTvEntity.formatGenres()
    val vote = movieTvEntity.voteAverage?.toString().emptyStringHandler()
    val year = movieTvEntity.releaseDate?.substringBefore("-").emptyStringHandler()
    navController.navigate("detail/$encodedUrl/$type/$name/$overview/$genres/$vote/$year") {
        launchSingleTop = true
    }
}
