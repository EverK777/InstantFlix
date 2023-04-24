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
class PopularRemoteMediator(
    private val localDataRepository: LocalDataRepository,
    private val remoteRepository: RemoteRepository,
    private val typeRequest: TypeRequest,
) : RemoteMediator<Int, MovieTvEntity>(), CachingMediator {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieTvEntity>,
    ): MediatorResult {
        val loadKey = when (loadType) {
            LoadType.REFRESH -> {
                1
            }

            LoadType.PREPEND -> {
                return MediatorResult.Success(
                    endOfPaginationReached = true,
                )
            }

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
            val popularMovies = remoteRepository.requestPopular(
                type = typeRequest.type,
                page = loadKey,
            )
        ) {
            is ApiResultHandle.Success -> {
                saveDataToCache(
                    value = popularMovies.value,
                    loadType = loadType,
                    typeRequest = typeRequest,
                    localDataRepository = localDataRepository,
                    requestCategory = RequestCategory.POPULAR,
                )
                MediatorResult.Success(endOfPaginationReached = popularMovies.value.result.isEmpty())
            }

            is ApiResultHandle.ApiError -> MediatorResult.Error(ApiException())
            is ApiResultHandle.NetworkError -> MediatorResult.Error(NetworkError())
        }
    }
}
