package com.challenge.instantflix.core.data.external.repository

import com.challenge.instantflix.core.data.model.GenreResponse
import com.challenge.instantflix.core.data.model.MovieTvResponse
import com.challenge.instantflix.core.data.model.TypeRequest
import com.challenge.instantflix.core.utils.ApiResultHandle

interface RemoteRepository {
    suspend fun requestTrendingMoviesSeries(typeRequest: TypeRequest): ApiResultHandle<MovieTvResponse>
    suspend fun requestTopRated(
        type: String,
        page: Int,
        sortBy: String,
    ): ApiResultHandle<MovieTvResponse>

    suspend fun requestPopular(
        type: String,
        page: Int,
    ): ApiResultHandle<MovieTvResponse>

    suspend fun requestByGenre(
        type: String,
        page: Int,
        genreId: Int,
    ): ApiResultHandle<MovieTvResponse>

    suspend fun requestGenres(type: String): ApiResultHandle<GenreResponse>
}
