package com.challenge.instantflix.core.data.internal.repository

import com.challenge.instantflix.core.data.internal.datasource.GenreDao
import com.challenge.instantflix.core.data.internal.datasource.MovieTVDao
import com.challenge.instantflix.core.data.model.Genre
import com.challenge.instantflix.core.data.model.MovieTvEntity
import com.challenge.instantflix.core.data.model.RequestCategory
import com.challenge.instantflix.core.data.model.TypeRequest

/**
 *A class that implements the [LocalDataRepository] interface and provides methods for accessing
 * and modifying local data stored in a database using Room.
 *@param movieTVDao an instance of [MovieTVDao] that provides access to the database.
 */
class InstantFlixDbRepository(
    private val movieTVDao: MovieTVDao,
    private val genreDao: GenreDao,
) : LocalDataRepository {

    override fun getMoviesOrTvShoesByCategoryAndTypeList(
        requestCategory: RequestCategory,
        typeRequest: TypeRequest,
        pageNumber: Int,
    ): List<MovieTvEntity> {
        return movieTVDao.getMoviesOrTvShoesByCategoryAndTypeList(
            requestCategory = requestCategory.valueName,
            typeRequest = typeRequest.type,
            pageNumber = pageNumber,
        )
    }

    /**
     * Inserts or updates a List [MovieTvEntity] List in the database.
     * @param movieTvEntities List of [MovieTvEntity] object to insert or update
     */
    override suspend fun upsertMovieOrTvCached(movieTvEntities: List<MovieTvEntity>) {
        movieTVDao.upsertMovieOrTvCached(movieTvEntities)
    }

    /**
     *Retrieves the cached [MovieTvEntity] with the given ID.
     *@param id The ID of the [MovieTvEntity] to retrieve.
     *@return The cached [MovieTvEntity] with the given ID.
     */
    override suspend fun getMovieTvCached(id: Int): MovieTvEntity? {
        return movieTVDao.getMovieTvCached(id)
    }

    /**
     *Inserts or updates the given [Genre] in the database.
     *@param genre The [Genre] to insert or update in the database.
     */
    override suspend fun upsertGenre(genre: Genre) {
        genreDao.upsertGenre(genre)
    }

    /**
     *Retrieves the [Genre] with the given ID.
     *@param genreId The ID of the [Genre] to retrieve.
     *@return The [Genre] with the given ID.
     */
    override suspend fun getGenre(genreId: Int): Genre? {
        return genreDao.getGenre(genreId)
    }

    override suspend fun getGenres(): List<Genre> {
        return genreDao.genres()
    }

    override suspend fun clearByCategoryAndTypeRequest(
        requestCategory: RequestCategory,
        typeRequest: TypeRequest,
    ) {
        movieTVDao.clearByCategoryAndType(requestCategory.valueName, typeRequest.type)
    }
}
