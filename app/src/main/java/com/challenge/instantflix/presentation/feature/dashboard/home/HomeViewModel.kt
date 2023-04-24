package com.challenge.instantflix.presentation.feature.dashboard.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.challenge.instantflix.core.data.model.MovieTvEntity
import com.challenge.instantflix.core.di.RequestPopularMovies
import com.challenge.instantflix.core.di.RequestTrendingMoviesAndTv
import com.challenge.instantflix.core.usecase.RequestPaginatedDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @RequestTrendingMoviesAndTv requestPaginatedDataUseCase: RequestPaginatedDataUseCase,
    @RequestPopularMovies requestPopularMovies: RequestPaginatedDataUseCase,
) : ViewModel() {
    //TODO: ADD MISSING UNIT TEST
    val trendingMoviesTvShowsPagerFlow: Flow<PagingData<MovieTvEntity>> =
        requestPaginatedDataUseCase
            .pager
            .flow
            .cachedIn(viewModelScope)

    val popularMoviesPagerFlow: Flow<PagingData<MovieTvEntity>> =
        requestPopularMovies
            .pager
            .flow
            .cachedIn(viewModelScope)
}
