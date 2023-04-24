package com.challenge.instantflix.core.data.external.mediators

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.challenge.instantflix.core.data.external.repository.RemoteRepository
import com.challenge.instantflix.core.data.internal.repository.LocalDataRepository
import com.challenge.instantflix.core.data.model.MovieTvEntity
import com.challenge.instantflix.core.data.model.RequestCategory
import com.challenge.instantflix.core.data.model.TypeRequest
import com.challenge.instantflix.core.exception.ApiException
import com.challenge.instantflix.core.exception.NetworkError
import com.challenge.instantflix.core.utils.ApiResultHandle

@OptIn(ExperimentalPagingApi::class)
class TrendingMoviesSeriesRemoteMediator(
    private val localDataRepository: LocalDataRepository,
    private val remoteRepository: RemoteRepository,
    private val typeRequest: TypeRequest,
) : RemoteMediator<Int, MovieTvEntity>(), CachingMediator {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieTvEntity>,
    ): MediatorResult {
        return when (
            val trendingMoviesAndTvShows =
                remoteRepository.requestTrendingMoviesSeries(typeRequest)
        ) {
            is ApiResultHandle.Success -> {
                saveDataToCache(
                    value = trendingMoviesAndTvShows.value,
                    loadType = loadType,
                    typeRequest = typeRequest,
                    localDataRepository = localDataRepository,
                    requestCategory = RequestCategory.TRENDING,
                )
                MediatorResult.Success(endOfPaginationReached = true)
            }

            is ApiResultHandle.ApiError -> MediatorResult.Error(ApiException())
            is ApiResultHandle.NetworkError -> MediatorResult.Error(NetworkError())
        }
    }
}
