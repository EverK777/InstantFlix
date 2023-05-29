package com.theblackbit.instantflix.core.data.pagingsource

import com.theblackbit.instantflix.core.data.internal.repository.LocalDataRepository
import com.theblackbit.instantflix.core.data.model.MovieTvResponse
import com.theblackbit.instantflix.core.data.model.RequestCategory
import com.theblackbit.instantflix.core.data.model.TypeRequest
import com.theblackbit.instantflix.core.data.model.toMovieTvEntity

interface CachingHandler {
    suspend fun saveDataToCache(
        moviesResponse: MovieTvResponse,
        currentPage: Int,
        typeRequest: TypeRequest,
        requestCategory: RequestCategory,
        localDataRepository: LocalDataRepository,
    ) {
        if (currentPage == 1) {
            localDataRepository.clearByCategoryAndTypeRequest(
                requestCategory = requestCategory,
                typeRequest = typeRequest,
            )
        }

        val entities = moviesResponse.result.map { movieTv ->
            val genres = movieTv.genreIds.map {
                localDataRepository.getGenre(it)?.name ?: ""
            }
            movieTv.toMovieTvEntity(
                requestCategory = requestCategory,
                typeRequest = typeRequest,
                page = currentPage,
                totalResult = moviesResponse.totalResults,
                genres = genres,
            )
        }
        localDataRepository.upsertMovieOrTvCached(entities)
    }
}
