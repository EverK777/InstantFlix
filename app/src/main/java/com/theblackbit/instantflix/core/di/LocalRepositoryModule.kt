package com.theblackbit.instantflix.core.di

import com.theblackbit.instantflix.core.data.internal.datasource.GenreDao
import com.theblackbit.instantflix.core.data.internal.datasource.MovieTVDao
import com.theblackbit.instantflix.core.data.internal.repository.InstantFlixDbRepository
import com.theblackbit.instantflix.core.data.internal.repository.LocalDataRepository
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
