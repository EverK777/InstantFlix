package com.challenge.instantflix.core.data.external.mediators

import androidx.paging.LoadType
import com.challenge.instantflix.core.data.internal.repository.LocalDataRepository
import com.challenge.instantflix.core.data.model.MovieTvResponse
import com.challenge.instantflix.core.data.model.RequestCategory
import com.challenge.instantflix.core.data.model.TypeRequest
import com.challenge.instantflix.core.data.model.toMovieTvEntity

interface CachingMediator {
    suspend fun saveDataToCache(
        value: MovieTvResponse,
        loadType: LoadType,
        typeRequest: TypeRequest,
        localDataRepository: LocalDataRepository,
    ) {
        val entities = value.result.map { movieTv ->
            movieTv.toMovieTvEntity(
                requestCategory = RequestCategory.POPULAR,
                typeRequest = typeRequest,
                page = value.page,
                totalResult = value.totalResults,
            )
        }
        if (loadType == LoadType.REFRESH) {
            localDataRepository.clearAll()
        }
        entities.forEach { movieTvEntity ->
            localDataRepository.upsertMovieOrTvCached(movieTvEntity)
        }
    }
}
