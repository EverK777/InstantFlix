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
class TopRatedRemoteMediator(
    private val localDataRepository: LocalDataRepository,
    private val remoteRepository: RemoteRepository,
    private val typeRequest: TypeRequest,
) : RemoteMediator<Int, MovieTvEntity>(), CachingMediator {

    companion object {
        const val SORT_BY_VOTE_AVERAGE_DESC = "vote_average.desc"
    }

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
            val topRatedMovies = remoteRepository.requestTopRated(
                typeRequest.type,
                page = loadKey,
                sortBy = SORT_BY_VOTE_AVERAGE_DESC,
            )
        ) {
            is ApiResultHandle.Success -> {
                saveDataToCache(
                    value = topRatedMovies.value,
                    loadType = loadType,
                    typeRequest = typeRequest,
                    localDataRepository = localDataRepository,
                    requestCategory = RequestCategory.TOP_RATED,
                )
                MediatorResult.Success(endOfPaginationReached = topRatedMovies.value.result.isEmpty())
            }

            is ApiResultHandle.ApiError -> MediatorResult.Error(ApiException())
            is ApiResultHandle.NetworkError -> MediatorResult.Error(NetworkError())
        }
    }
}
