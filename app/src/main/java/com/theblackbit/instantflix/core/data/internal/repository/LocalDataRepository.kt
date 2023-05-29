package com.theblackbit.instantflix.core.data.internal.repository

import com.theblackbit.instantflix.core.data.model.Genre
import com.theblackbit.instantflix.core.data.model.MovieTvEntity
import com.theblackbit.instantflix.core.data.model.RequestCategory
import com.theblackbit.instantflix.core.data.model.TypeRequest

interface LocalDataRepository {

    fun getMoviesOrTvShoesByCategoryAndTypeList(
        requestCategory: RequestCategory,
        typeRequest: TypeRequest,
        pageNumber: Int,
    ): List<MovieTvEntity>

    suspend fun upsertMovieOrTvCached(movieTvEntities: List<MovieTvEntity>)

    suspend fun getMovieTvCached(id: Int): MovieTvEntity?
    suspend fun upsertGenre(genre: Genre)
    suspend fun getGenres(): List<Genre>
    suspend fun getGenre(genreId: Int): Genre?
    suspend fun clearByCategoryAndTypeRequest(
        requestCategory: RequestCategory,
        typeRequest: TypeRequest,
    )
}
