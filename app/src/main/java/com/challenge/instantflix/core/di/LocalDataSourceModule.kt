package com.challenge.instantflix.core.di

import android.content.Context
import androidx.room.Room
import com.challenge.instantflix.core.data.internal.datasource.DBConverters
import com.challenge.instantflix.core.data.internal.datasource.GenreDao
import com.challenge.instantflix.core.data.internal.datasource.InstantFlixDB
import com.challenge.instantflix.core.data.internal.datasource.MovieTVDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataSourceModule {

    @Provides
    @Singleton
    fun providesLocalDataSource(@ApplicationContext context: Context): InstantFlixDB {
        return Room.databaseBuilder(
            context,
            InstantFlixDB::class.java,
            "instant_flix_db",
        )
            .addTypeConverter(DBConverters())
            .build()
    }

    @Provides
    @Singleton
    fun providesMovieTVDao(instantFlixDB: InstantFlixDB): MovieTVDao {
        return instantFlixDB.movieTBDao()
    }

    @Provides
    @Singleton
    fun providesGenreDao(instantFlixDB: InstantFlixDB): GenreDao {
        return instantFlixDB.genreDao()
    }
}
