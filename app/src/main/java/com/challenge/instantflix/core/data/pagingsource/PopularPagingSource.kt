package com.challenge.instantflix.core.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.challenge.instantflix.core.data.external.repository.RemoteRepository
import com.challenge.instantflix.core.data.internal.repository.LocalDataRepository
import com.challenge.instantflix.core.data.model.MovieTvEntity
import com.challenge.instantflix.core.data.model.RequestCategory
import com.challenge.instantflix.core.data.model.TypeRequest
import com.challenge.instantflix.core.exception.ApiException
import com.challenge.instantflix.core.exception.NetworkError
import com.challenge.instantflix.core.utils.ApiResultHandle

class PopularPagingSource(
    private val localDataRepository: LocalDataRepository,
    private val remoteRepository: RemoteRepository,
    private val typeRequest: TypeRequest,
) : PagingSource<Int, MovieTvEntity>(), CachingHandler {

    override fun getRefreshKey(state: PagingState<Int, MovieTvEntity>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieTvEntity> {
        return try {
            val currentPage = params.key ?: 1

            var exception: Throwable? = null

            var apiFinishedItems = false

            when (
                val listReturnedFromApi =
                    remoteRepository.requestPopular(TypeRequest.MOVIE.type, currentPage)
            ) {
                is ApiResultHandle.Success -> {
                    saveDataToCache(
                        moviesResponse = listReturnedFromApi.value,
                        currentPage = currentPage,
                        typeRequest = typeRequest,
                        requestCategory = RequestCategory.POPULAR,
                        localDataRepository = localDataRepository,
                    )
                }

                is ApiResultHandle.ApiError -> exception = ApiException()
                is ApiResultHandle.NetworkError -> exception = NetworkError()
            }

            val listCached = localDataRepository.getMoviesAndTvShowsByCategoryList(
                requestCategory = RequestCategory.POPULAR,
                pageNumber = currentPage,
            )

            if (listCached.isEmpty() && exception != null) {
                LoadResult.Error(exception)
            } else {
                LoadResult.Page(
                    data = listCached,
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = if (listCached.isEmpty()) null else currentPage + 1,
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
