package com.challenge.instantflix.core.data.external.mediators

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.challenge.instantflix.core.data.external.repository.RemoteRepository
import com.challenge.instantflix.core.data.internal.repository.LocalDataRepository
import com.challenge.instantflix.core.data.model.MovieTvEntity
import com.challenge.instantflix.core.data.model.TypeRequest
import com.challenge.instantflix.core.exception.ApiException
import com.challenge.instantflix.core.exception.NetworkError
import com.challenge.instantflix.core.utils.ApiResultHandle

@OptIn(ExperimentalPagingApi::class)
class ByGenreRemoteMediator(
    private val genreId: Int,
    private val localDataRepository: LocalDataRepository,
    private val remoteRepository: RemoteRepository,
    private val typeRequest: TypeRequest,
) : RemoteMediator<Int, MovieTvEntity>(), CachingMediator {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieTvEntity>,
    ): MediatorResult {
        val loadKey = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> return MediatorResult.Success(
                endOfPaginationReached = true,
            )

            LoadType.APPEND -> {
                val lastItem = state.lastItemOrNull()
                if (lastItem == null) {
                    1
                } else {
                    (lastItem.page ?: 0) + 1
                }
            }
        }

        return when (
            val moviesSeriesByGenre = remoteRepository.requestByGenre(
                type = typeRequest.type,
                page = loadKey,
                genreId = genreId,
            )
        ) {
            is ApiResultHandle.Success -> {
                saveDataToCache(
                    value = moviesSeriesByGenre.value,
                    loadType = loadType,
                    typeRequest = typeRequest,
                    localDataRepository = localDataRepository,
                )
                MediatorResult.Success(endOfPaginationReached = moviesSeriesByGenre.value.result.isEmpty())
            }
            is ApiResultHandle.ApiError -> MediatorResult.Error(ApiException())
            is ApiResultHandle.NetworkError -> MediatorResult.Error(NetworkError())
        }
    }
}
