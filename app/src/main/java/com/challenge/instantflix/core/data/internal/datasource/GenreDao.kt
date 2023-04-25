package com.challenge.instantflix.core.data.internal.datasource

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.challenge.instantflix.core.data.model.Genre

@Dao
interface GenreDao {

    @Query("SELECT * from genre")
    suspend fun genres(): List<Genre>

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
}
