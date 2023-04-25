package com.challenge.instantflix.core.data.internal.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.challenge.instantflix.core.data.model.Genre
import com.challenge.instantflix.core.data.model.MovieTvEntity

@Database(
    entities = [
        MovieTvEntity::class,
        Genre::class,
    ],
    version = 1,
    exportSchema = true,
)

@TypeConverters(DBConverters::class)
abstract class InstantFlixDB : RoomDatabase() {
    abstract fun movieTBDao(): MovieTVDao
    abstract fun genreDao(): GenreDao
}
