package com.challenge.instantflix.core.data.internal.repository

import androidx.paging.PagingSource
import com.challenge.instantflix.core.data.model.Genre
import com.challenge.instantflix.core.data.model.MovieTvEntity
import com.challenge.instantflix.core.data.model.RequestCategory
import com.challenge.instantflix.core.data.model.TypeRequest

interface LocalDataRepository {
    fun getMoviesAndTvShowsByCategory(requestCategory: RequestCategory): PagingSource<Int, MovieTvEntity>
    fun getMoviesOrTvShoesByCategoryAndType(
        requestCategory: RequestCategory,
        typeRequest: TypeRequest,
    ): PagingSource<Int, MovieTvEntity>

    fun getMoviesAndTvShowsByGenre(genreId: Int): PagingSource<Int, MovieTvEntity>
    fun getMoviesOrTvShowsByGenreAndType(
        genreId: Int,
        typeRequest: TypeRequest,
    ): PagingSource<Int, MovieTvEntity>

    suspend fun upsertMovieOrTvCached(movieTvEntities: List<MovieTvEntity>)

    suspend fun getMovieTvCached(id: Int): MovieTvEntity?
    suspend fun upsertGenre(genre: Genre)
    suspend fun getGenres(): List<Genre>
    suspend fun getGenre(genreId: Int): Genre?
    suspend fun clearAll()
}
