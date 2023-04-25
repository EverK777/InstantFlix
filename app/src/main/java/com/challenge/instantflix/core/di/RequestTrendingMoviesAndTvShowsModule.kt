package com.challenge.instantflix.core.di

import androidx.paging.Pager
import com.challenge.instantflix.core.data.model.MovieTvEntity
import com.challenge.instantflix.core.usecase.RequestPaginatedDataUseCase
import com.challenge.instantflix.core.usecase.RequestPaginatedDataUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Qualifier

@Module
@InstallIn(ViewModelComponent::class)
object RequestTrendingMoviesAndTvShowsModule {
    @RequestTrendingMoviesAndTv
    @ViewModelScoped
    @Provides
    fun providesRequestTrendingMoviesAndTvShowsUseCase(
        @TrendingMoviesAndTvShowsPager pager: Pager<Int, MovieTvEntity>,
    ): RequestPaginatedDataUseCase {
        return RequestPaginatedDataUseCaseImpl(pager)
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RequestTrendingMoviesAndTv
