package com.challenge.instantflix.core.usecase

import androidx.paging.Pager
import com.challenge.instantflix.core.data.model.MovieTvEntity

class RequestTrendingMoviesAndTvShowsUseCaseImpl(
    private val trendingMoviesTvShowsPager: Pager<Int, MovieTvEntity>,
) : RequestTrendingMoviesAndTvShowsUseCase {
    override val trendingMoviesAndTvShowsPager: Pager<Int, MovieTvEntity>
        get() = trendingMoviesTvShowsPager
}
