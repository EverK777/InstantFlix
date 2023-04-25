package com.challenge.instantflix.presentation.feature.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.challenge.instantflix.presentation.feature.dashboard.home.HomeScreen
import com.challenge.instantflix.presentation.feature.dashboard.home.HomeViewModel
import com.challenge.instantflix.presentation.feature.detail.DetailView
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainGraph() {
    val navController = rememberAnimatedNavController()

    AnimatedNavHost(
        navController = navController,
        startDestination = Routes.HomeRoute.route,
    ) {
        navigationItems.forEach { routes ->
            when (routes) {
                Routes.HomeRoute -> {
                    slideRoute(
                        route = routes.route,
                    ) {
                        val viewModel = hiltViewModel<HomeViewModel>()
                        val trending =
                            viewModel.trendingMoviesTvShowsFlow.collectAsStateWithLifecycle()
                        val popularMovies =
                            viewModel.popularMoviesPagerFlow.collectAsLazyPagingItems()
                        val popularTvShows = viewModel.popularTvShowsFlow.collectAsLazyPagingItems()
                        val topRatedMovies = viewModel.topRatedMoviesFlow.collectAsLazyPagingItems()
                        val topRatedTvShows =
                            viewModel.topRatedTvShowsFlow.collectAsLazyPagingItems()

                        HomeScreen(
                            movieTvEntity = { trending.value },
                            popularMovies = popularMovies,
                            popularTvShows = popularTvShows,
                            topRatedMovies = topRatedMovies,
                            topRatedTvShows = topRatedTvShows,
                            navController = navController,
                        )
                    }
                }

                Routes.DetailRoute -> {
                    slideRoute(
                        route = routes.route,
                        arguments = routes.arguments,
                    ) { backStackEntry ->
                        val poster =
                            backStackEntry.arguments?.getString(Routes.DetailRoute.arguments[0].name)
                                ?: ""
                        val typeRequest =
                            backStackEntry.arguments?.getString(Routes.DetailRoute.arguments[1].name)
                                ?: ""
                        val name =
                            backStackEntry.arguments?.getString(Routes.DetailRoute.arguments[2].name)
                                ?: ""
                        val override =
                            backStackEntry.arguments?.getString(Routes.DetailRoute.arguments[3].name)
                                ?: ""
                        val genres =
                            backStackEntry.arguments?.getString(Routes.DetailRoute.arguments[4].name)
                                ?: ""
                        val vote =
                            backStackEntry.arguments?.getString(Routes.DetailRoute.arguments[5].name)
                                ?: ""
                        val year =
                            backStackEntry.arguments?.getString(Routes.DetailRoute.arguments[6].name)
                                ?: ""

                        DetailView(
                            posterImage = poster,
                            typeRequest = typeRequest,
                            name = name,
                            genres = genres,
                            overview = override,
                            voteAverage = vote,
                            releaseYear = year,
                            navHostController = navController,
                        )
                    }
                }
            }
        }
    }
}
