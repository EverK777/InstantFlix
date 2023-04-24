package com.challenge.instantflix.core.di

import androidx.paging.Pager
import com.challenge.instantflix.core.data.model.MovieTvEntity
import com.challenge.instantflix.core.usecase.RequestTrendingMoviesAndTvShowsUseCase
import com.challenge.instantflix.core.usecase.RequestTrendingMoviesAndTvShowsUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RequestTrendingMoviesAndTvShowsUseCaseModule {

    @ViewModelScoped
    @Provides
    fun providesRequestTrendingMoviesAndTvShowsUseCase(
        @TrendingMoviesAndTvShowsPager pager: Pager<Int, MovieTvEntity>,
    ): RequestTrendingMoviesAndTvShowsUseCase {
        return RequestTrendingMoviesAndTvShowsUseCaseImpl(pager)
    }
}
