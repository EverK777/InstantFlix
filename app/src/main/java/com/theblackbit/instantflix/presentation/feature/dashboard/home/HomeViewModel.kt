package com.theblackbit.instantflix.presentation.feature.dashboard.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.theblackbit.instantflix.core.data.model.MovieTvEntity
import com.theblackbit.instantflix.core.data.model.TypeRequest
import com.theblackbit.instantflix.core.di.IoDispatcher
import com.theblackbit.instantflix.core.di.MainDispatcher
import com.theblackbit.instantflix.core.di.RequestPopularMovies
import com.theblackbit.instantflix.core.di.RequestPopularTvShows
import com.theblackbit.instantflix.core.di.RequestTopRatedMovies
import com.theblackbit.instantflix.core.di.RequestTopRatedTvShows
import com.theblackbit.instantflix.core.usecase.RequestPaginatedDataUseCase
import com.theblackbit.instantflix.core.usecase.TrendingHandlerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    trendingHandlerUseCase: TrendingHandlerUseCase,
    @IoDispatcher ioDispatcher: CoroutineDispatcher,
    @MainDispatcher mainDispatcher: CoroutineDispatcher,
    @RequestPopularMovies requestPopularMovies: RequestPaginatedDataUseCase,
    @RequestPopularTvShows requestPopularTvShows: RequestPaginatedDataUseCase,
    @RequestTopRatedMovies requestTopRatedMovies: RequestPaginatedDataUseCase,
    @RequestTopRatedTvShows requestTopRatedTvShows: RequestPaginatedDataUseCase,
) : ViewModel() {

    private val _trendingMoviesTvShowsFlow: MutableStateFlow<MovieTvEntity?> =
        MutableStateFlow(null)

    val trendingMoviesTvShowsFlow: StateFlow<MovieTvEntity?> = _trendingMoviesTvShowsFlow

    val popularMoviesPagerFlow: Flow<PagingData<MovieTvEntity>> =
        requestPopularMovies
            .pager
            .flow
            .cachedIn(viewModelScope)

    val popularTvShowsFlow: Flow<PagingData<MovieTvEntity>> =
        requestPopularTvShows
            .pager
            .flow
            .cachedIn(viewModelScope)

    val topRatedMoviesFlow: Flow<PagingData<MovieTvEntity>> =
        requestTopRatedMovies
            .pager
            .flow
            .cachedIn(viewModelScope)

    val topRatedTvShowsFlow: Flow<PagingData<MovieTvEntity>> =
        requestTopRatedTvShows
            .pager
            .flow
            .cachedIn(viewModelScope)

    init {
        viewModelScope.launch(ioDispatcher) {
            val tendingMovieTv = trendingHandlerUseCase.getTrendingMovieTvShow(TypeRequest.ALL)
            withContext(mainDispatcher) {
                _trendingMoviesTvShowsFlow.value = tendingMovieTv
            }
        }
    }
}
