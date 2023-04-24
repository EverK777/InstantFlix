package com.challenge.instantflix.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.challenge.instantflix.presentation.feature.dashboard.home.HomeScreen
import com.challenge.instantflix.presentation.feature.dashboard.home.HomeViewModel
import com.challenge.instantflix.presentation.ui.theme.InstantflixTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InstantflixTheme(darkTheme = true) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    val viewModel = hiltViewModel<HomeViewModel>()
                    val trending = viewModel.trendingMoviesTvShowsFlow.collectAsStateWithLifecycle()
                    val popularMovies = viewModel.popularMoviesPagerFlow.collectAsLazyPagingItems()
                    val popularTvShows = viewModel.popularTvShowsFlow.collectAsLazyPagingItems()
                    val topRatedMovies = viewModel.topRatedMoviesFlow.collectAsLazyPagingItems()
                    val topRatedTvShows = viewModel.topRatedTvShowsFlow.collectAsLazyPagingItems()
                    HomeScreen(
                        movieTvEntity = { trending.value },
                        popularMovies = popularMovies,
                        popularTvShows = popularTvShows,
                        topRatedMovies = topRatedMovies,
                        topRatedTvShows = topRatedTvShows,
                    )
                }
            }
        }
    }
}
