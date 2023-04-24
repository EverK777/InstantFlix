package com.challenge.instantflix.core.data.internal.repository

import androidx.paging.PagingSource
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
) : LocalDataRepository {

    /**
     *Returns a [PagingSource] that provides a stream of [MovieTvEntity] objects from the database, filtered by the specified [RequestCategory].
     *@param requestCategory the [RequestCategory] to filter the results by.
     *@return a [PagingSource] of [MovieTvEntity] objects that match the specified category.
     */
    override fun getMoviesAndTvShowsByCategory(requestCategory: RequestCategory): PagingSource<Int, MovieTvEntity> {
        return movieTVDao.getMoviesAndTvShowsByCategory(requestCategory.valueName)
    }

    /**
     *Returns a [PagingSource] that provides a stream of [MovieTvEntity] objects from the database, filtered by the specified [RequestCategory] and [TypeRequest].
     *@param requestCategory the [RequestCategory] to filter the results by.
     *@param typeRequest the [TypeRequest] to filter the results by.
     *@return a [PagingSource] of [MovieTvEntity] objects that match the specified category and type.
     */
    override fun getMoviesOrTvShoesByCategoryAndType(
        requestCategory: RequestCategory,
        typeRequest: TypeRequest,
    ): PagingSource<Int, MovieTvEntity> {
        return movieTVDao.getMoviesOrTvShoesByCategoryAndType(
            requestCategory = requestCategory.valueName,
            typeRequest = typeRequest.type,
        )
    }

    /**
     *Returns a [PagingSource] that provides a stream of [MovieTvEntity] objects from the database, filtered by the specified genre ID.
     *@param genreId the ID of the genre to filter the results by.
     *@return a [PagingSource] of [MovieTvEntity] objects that match the specified genre ID.
     */
    override fun getMoviesAndTvShowsByGenre(genreId: Int): PagingSource<Int, MovieTvEntity> {
        return movieTVDao.getMoviesAndTvShowsByGenre(genreId)
    }

    /**
     *Returns a [PagingSource] that provides a stream of [MovieTvEntity] objects from the database, filtered by the specified genre ID and [TypeRequest].
     *@param genreId the ID of the genre to filter the results by.
     *@param typeRequest the [TypeRequest] to filter the results by.
     *@return a [PagingSource] of [MovieTvEntity] objects that match the specified genre ID and type.
     */
    override fun getMoviesOrTvShowsByGenreAndType(
        genreId: Int,
        typeRequest: TypeRequest,
    ): PagingSource<Int, MovieTvEntity> {
        return movieTVDao.getMoviesOrTvShowsByGenreAndType(
            genreId = genreId,
            typeRequest = typeRequest.type,
        )
    }

    /**
     * Inserts or updates a [MovieTvEntity] List in the database.
     * @param movieTvEntities List of [MovieTvEntity] object to insert or update
     */
    //TODO: FIX TEST
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
        movieTVDao.upsertGenre(genre)
    }

    /**
     *Retrieves the [Genre] with the given ID.
     *@param genreId The ID of the [Genre] to retrieve.
     *@return The [Genre] with the given ID.
     */
    override suspend fun getGenre(genreId: Int): Genre? {
        return movieTVDao.getGenre(genreId)
    }

    //TODO: ADD UNIT TEST AND COMMENTS
    override suspend fun getGenres(): List<Genre> {
        return movieTVDao.genres()
    }

    /**
     *Deletes all data from the database.
     */
    override suspend fun clearAll() {
        movieTVDao.clearAll()
    }
}
