package com.challenge.instantflix.presentation.feature.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.challenge.instantflix.core.data.model.MovieTvEntity
import com.challenge.instantflix.core.data.model.formatGenres
import com.challenge.instantflix.core.data.model.getImagePoster
import com.challenge.instantflix.core.utils.emptyStringHandler
import com.challenge.instantflix.core.utils.slashReplacer
import com.challenge.instantflix.presentation.feature.dashboard.home.HomeScreen
import com.challenge.instantflix.presentation.feature.dashboard.home.HomeViewModel
import com.challenge.instantflix.presentation.feature.detail.DetailView
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

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
                        val trending = viewModel.trendingMoviesTvShowsFlow
                        val popularMovies = viewModel.popularMoviesPagerFlow
                        val popularTvShows = viewModel.popularTvShowsFlow
                        val topRatedMovies = viewModel.topRatedMoviesFlow
                        val topRatedTvShows = viewModel.topRatedTvShowsFlow

                        HomeScreen(
                            movieTvEntity = trending,
                            popularMovies = popularMovies,
                            popularTvShows = popularTvShows,
                            topRatedMovies = topRatedMovies,
                            topRatedTvShows = topRatedTvShows,
                        ) { movieTv ->
                            navigateToDetail(movieTv, navController)
                        }
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
                        ) {
                            navController.popBackStack()
                        }
                    }
                }
            }
        }
    }
}

private fun navigateToDetail(movieTvEntity: MovieTvEntity, navController: NavHostController) {
    val poster = movieTvEntity.getImagePoster()
    val encodedUrl = URLEncoder.encode(poster, StandardCharsets.UTF_8.toString())
    val type = movieTvEntity.typeRequest.type
    val name = movieTvEntity.title.emptyStringHandler().slashReplacer()
    val overview = movieTvEntity.overview.emptyStringHandler().slashReplacer()
    val genres = movieTvEntity.formatGenres().slashReplacer()
    val vote = movieTvEntity.voteAverage?.toString().emptyStringHandler().slashReplacer()
    val year = movieTvEntity.releaseDate?.substringBefore("-").emptyStringHandler()
    navController.navigate("detail/$encodedUrl/$type/$name/$overview/$genres/$vote/$year") {
        launchSingleTop = true
    }
}
