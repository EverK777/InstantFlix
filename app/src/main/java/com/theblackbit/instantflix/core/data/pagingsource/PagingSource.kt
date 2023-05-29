package com.theblackbit.instantflix.core.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.theblackbit.instantflix.core.data.internal.repository.LocalDataRepository
import com.theblackbit.instantflix.core.data.model.MovieTvEntity
import com.theblackbit.instantflix.core.data.model.MovieTvResponse
import com.theblackbit.instantflix.core.data.model.RequestCategory
import com.theblackbit.instantflix.core.data.model.TypeRequest
import com.theblackbit.instantflix.core.exception.ApiException
import com.theblackbit.instantflix.core.exception.NetworkError
import com.theblackbit.instantflix.core.utils.ApiResultHandle
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class PagingSource(
    private val localDataRepository: LocalDataRepository,
    private val typeRequest: TypeRequest,
    private val requestCategory: RequestCategory,
    private val dispatcher: CoroutineDispatcher,
    private val apiRequest: suspend (page: Int) -> ApiResultHandle<MovieTvResponse>,
) : PagingSource<Int, MovieTvEntity>(), CachingHandler {

    override fun getRefreshKey(state: PagingState<Int, MovieTvEntity>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieTvEntity> {
        return try {
            withContext(dispatcher) {
                val currentPage = params.key ?: 1

                var exception: Throwable? = null

                when (
                    val listReturnedFromApi = apiRequest.invoke(currentPage)
                ) {
                    is ApiResultHandle.Success -> {
                        saveDataToCache(
                            moviesResponse = listReturnedFromApi.value,
                            currentPage = currentPage,
                            typeRequest = typeRequest,
                            requestCategory = requestCategory,
                            localDataRepository = localDataRepository,
                        )
                    }

                    is ApiResultHandle.ApiError -> exception = ApiException()
                    is ApiResultHandle.NetworkError -> exception = NetworkError()
                }

                val listCached = localDataRepository.getMoviesOrTvShoesByCategoryAndTypeList(
                    requestCategory = requestCategory,
                    typeRequest = typeRequest,
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
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
