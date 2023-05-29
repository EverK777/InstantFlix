package com.theblackbit.instantflix.core.di

import androidx.paging.Pager
import com.theblackbit.instantflix.core.data.model.MovieTvEntity
import com.theblackbit.instantflix.core.usecase.RequestPaginatedDataUseCase
import com.theblackbit.instantflix.core.usecase.RequestPaginatedDataUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Qualifier

@Module
@InstallIn(ViewModelComponent::class)
object RequestTopRatedMoviesModule {

    @RequestTopRatedMovies
    @ViewModelScoped
    @Provides
    fun providesRequestTrendingMoviesAndTvShowsUseCase(
        @TopRatedMovies pager: Pager<Int, MovieTvEntity>,
    ): RequestPaginatedDataUseCase {
        return RequestPaginatedDataUseCaseImpl(pager)
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RequestTopRatedMovies
