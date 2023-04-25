package com.challenge.instantflix.core.data.internal.datasource

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.challenge.instantflix.core.data.model.MovieTvEntity

/**
 * Data Access Object (DAO) for accessing and modifying data in the MovieTVEntity and Genre tables in the database.
 */
@Dao
interface MovieTVDao {

    @Query("SELECT * FROM movietventity where requestCategory = :requestCategory AND typeRequest = :typeRequest and page = :pageNumber")
    fun getMoviesOrTvShoesByCategoryAndTypeList(
        requestCategory: String,
        typeRequest: String,
        pageNumber: Int,
    ): List<MovieTvEntity>

    /**
     * Returns a [MovieTvEntity] object with the specified ID.
     * @param id the ID of the [MovieTvEntity] to retrieve
     * @return the [MovieTvEntity] with the specified ID
     */
    @Query("SELECT * FROM movietventity where id = :id")
    suspend fun getMovieTvCached(id: Int): MovieTvEntity?

    /**
     * Inserts or updates a [MovieTvEntity] List in the database.
     * @param movieTvEntities List of [MovieTvEntity] object to insert or update
     */

    @Upsert
    suspend fun upsertMovieOrTvCached(movieTvEntities: List<MovieTvEntity>)

    @Query("DELETE FROM movietventity where requestCategory = :requestCategory and typeRequest = :typeRequest")
    suspend fun clearByCategoryAndType(requestCategory: String, typeRequest: String)
}
