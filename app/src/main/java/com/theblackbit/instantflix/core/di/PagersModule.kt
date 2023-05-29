package com.theblackbit.instantflix.core.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.theblackbit.instantflix.core.data.external.repository.RemoteRepository
import com.theblackbit.instantflix.core.data.internal.repository.LocalDataRepository
import com.theblackbit.instantflix.core.data.model.MovieTvEntity
import com.theblackbit.instantflix.core.data.model.RequestCategory
import com.theblackbit.instantflix.core.data.model.TypeRequest
import com.theblackbit.instantflix.core.data.pagingsource.PagingSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Qualifier
import javax.inject.Singleton

@ExperimentalPagingApi
@Module
@InstallIn(SingletonComponent::class)
object PagersModule {

    private const val SORT_BY_VOTE_AVERAGE_DESC = "vote_average.desc"
    @PopularMovies
    @Provides
    @Singleton
    fun providesPopularMovies(
        localDataRepository: LocalDataRepository,
        remoteRepository: RemoteRepository,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): Pager<Int, MovieTvEntity> {
        val pagingSource = PagingSource(
            localDataRepository = localDataRepository,
            typeRequest = TypeRequest.MOVIE,
            requestCategory = RequestCategory.POPULAR,
            dispatcher = ioDispatcher,
            apiRequest = { page ->
                remoteRepository.requestPopular(
                    type = TypeRequest.MOVIE.type,
                    page = page
                )
            }
        )
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                pagingSource
            },
        )
    }

    @PopularTvShows
    @Provides
    @Singleton
    fun providesPopularTvShows(
        localDataRepository: LocalDataRepository,
        remoteRepository: RemoteRepository,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): Pager<Int, MovieTvEntity> {
        val pagingSource = PagingSource(
            localDataRepository = localDataRepository,
            typeRequest = TypeRequest.TV_SHOW,
            requestCategory = RequestCategory.POPULAR,
            dispatcher = ioDispatcher,
            apiRequest = { page ->
                remoteRepository.requestPopular(
                    type = TypeRequest.TV_SHOW.type,
                    page = page
                )
            }
        )
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                pagingSource
            },
        )
    }

    @TopRatedMovies
    @Provides
    @Singleton
    fun providesTopRatedMovies(
        localDataRepository: LocalDataRepository,
        remoteRepository: RemoteRepository,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): Pager<Int, MovieTvEntity> {
        val pagingSource = PagingSource(
            localDataRepository = localDataRepository,
            typeRequest = TypeRequest.MOVIE,
            requestCategory = RequestCategory.TOP_RATED,
            dispatcher = ioDispatcher,
            apiRequest = { page ->
                remoteRepository.requestTopRated(
                    type = TypeRequest.MOVIE.type,
                    page = page,
                    sortBy = SORT_BY_VOTE_AVERAGE_DESC
                )
            }
        )
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                pagingSource
            },
        )
    }

    @TopRatedTvShows
    @Provides
    @Singleton
    fun providesTopRatedTvShows(
        localDataRepository: LocalDataRepository,
        remoteRepository: RemoteRepository,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): Pager<Int, MovieTvEntity> {
        val pagingSource = PagingSource(
            localDataRepository = localDataRepository,
            typeRequest = TypeRequest.TV_SHOW,
            requestCategory = RequestCategory.TOP_RATED,
            dispatcher = ioDispatcher,
            apiRequest = { page ->
                remoteRepository.requestTopRated(
                    type = TypeRequest.TV_SHOW.type,
                    page = page,
                    sortBy = SORT_BY_VOTE_AVERAGE_DESC
                )
            }
        )
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                pagingSource
            },
        )
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TrendingMoviesAndTvShowsPager

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class PopularMovies

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class PopularTvShows

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TopRatedMovies

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TopRatedTvShows

