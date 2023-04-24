package com.challenge.instantflix.core.usecase

import androidx.paging.Pager
import com.challenge.instantflix.core.data.model.MovieTvEntity

interface RequestTrendingMoviesAndTvShowsUseCase {
    val trendingMoviesAndTvShowsPager: Pager<Int, MovieTvEntity>
}
