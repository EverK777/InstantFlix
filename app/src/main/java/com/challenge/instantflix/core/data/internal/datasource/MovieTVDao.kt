package com.challenge.instantflix.core.data.internal.datasource

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.challenge.instantflix.core.data.model.Genre
import com.challenge.instantflix.core.data.model.MovieTvEntity

/**
 * Data Access Object (DAO) for accessing and modifying data in the MovieTVEntity and Genre tables in the database.
 */
@Dao
interface MovieTVDao {

    /**
     * Returns a [PagingSource] for a list of [MovieTvEntity]  that belong to a specific category.
     * @param requestCategory the category to filter the results by
     * @return a [PagingSource] for the requested list of [MovieTvEntity]
     */
    @Query("SELECT * FROM movietventity where requestCategory = :requestCategory")
    fun getMoviesAndTvShowsByCategory(requestCategory: String): PagingSource<Int, MovieTvEntity>

    /**
     * Returns a [PagingSource] for a list of [MovieTvEntity] objects that belong to a specific category and type (movie or TV show).
     * @param requestCategory the category to filter the results by
     * @param typeRequest the type of request (movie or TV show) to filter the results by
     * @return a [PagingSource] for the requested list of [MovieTvEntity] objects
     */
    @Query("SELECT * FROM movietventity where requestCategory = :requestCategory AND typeRequest = :typeRequest")
    fun getMoviesOrTvShoesByCategoryAndType(
        requestCategory: String,
        typeRequest: String,
    ): PagingSource<Int, MovieTvEntity>

    /**
     * Returns a [PagingSource] for a list of [MovieTvEntity] objects that belong to a specific genre.
     * @param genreId the genre ID to filter the results by
     * @return a [PagingSource] for the requested list of [MovieTvEntity] objects
     */
    @Query("SELECT * FROM movietventity where genreIdRequested = :genreId")
    fun getMoviesAndTvShowsByGenre(genreId: Int): PagingSource<Int, MovieTvEntity>

    /**
     * Returns a [PagingSource] for a list of [MovieTvEntity] objects that belong to a specific genre and type (movie or TV show).
     * @param genreId the genre ID to filter the results by
     * @param typeRequest the type of request (movie or TV show) to filter the results by
     * @return a [PagingSource] for the requested list of [MovieTvEntity] objects
     */
    @Query("SELECT * FROM movietventity where genreIdRequested = :genreId AND typeRequest = :typeRequest")
    fun getMoviesOrTvShowsByGenreAndType(
        genreId: Int,
        typeRequest: String,
    ): PagingSource<Int, MovieTvEntity>

    /**
     * Returns a [MovieTvEntity] object with the specified ID.
     * @param id the ID of the [MovieTvEntity] to retrieve
     * @return the [MovieTvEntity] with the specified ID
     */
    @Query("SELECT * FROM movietventity where id = :id")
    suspend fun getMovieTvCached(id: Int): MovieTvEntity?

    /**
     * Inserts or updates a [MovieTvEntity] object in the database.
     * @param movieTvEntity the [MovieTvEntity] object to insert or update
     */
    @Upsert
    suspend fun upsertMovieOrTvCached(movieTvEntity: MovieTvEntity)

    /**
     * Inserts or updates a [Genre] object in the database.
     * @param genre the [Genre] object to insert or update
     */
    @Upsert
    suspend fun upsertGenre(genre: Genre)

    /**
     * Retrieves a [Genre] from the local database with the given genre ID.
     * @param genreId The ID of the genre to retrieve.
     * @return A [Genre] object representing the genre with the given ID.
     */
    @Query("SELECT * FROM genre where id = :genreId")
    suspend fun getGenre(genreId: Int): Genre?

    /**
     * This method deletes all data from the movietventity table in the local database.
     */
    @Query("DELETE FROM movietventity")
    suspend fun clearAll()
}
