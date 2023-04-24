package com.challenge.instantflix.presentation.feature.dashboard.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.challenge.instantflix.core.data.model.MovieTvEntity
import com.challenge.instantflix.core.usecase.RequestTrendingMoviesAndTvShowsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val requestTrendingMoviesAndTvShowsUseCase: RequestTrendingMoviesAndTvShowsUseCase,
) : ViewModel() {

    val trendingMoviesTvShowsPagerFlow: Flow<PagingData<MovieTvEntity>> =
        requestTrendingMoviesAndTvShowsUseCase
            .trendingMoviesAndTvShowsPager
            .flow
            .cachedIn(viewModelScope)
}
