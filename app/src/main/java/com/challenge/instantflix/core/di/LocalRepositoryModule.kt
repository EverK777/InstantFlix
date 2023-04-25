package com.challenge.instantflix.core.di

import com.challenge.instantflix.core.data.internal.datasource.GenreDao
import com.challenge.instantflix.core.data.internal.datasource.MovieTVDao
import com.challenge.instantflix.core.data.internal.repository.InstantFlixDbRepository
import com.challenge.instantflix.core.data.internal.repository.LocalDataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalRepositoryModule {

    @Provides
    @Singleton
    fun providesLocalRepository(
        movieTVDao: MovieTVDao,
        genreDao: GenreDao,
    ): LocalDataRepository {
        return InstantFlixDbRepository(movieTVDao, genreDao)
    }
}
