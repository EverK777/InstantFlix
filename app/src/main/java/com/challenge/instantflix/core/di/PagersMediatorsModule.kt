package com.challenge.instantflix.core.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.challenge.instantflix.core.data.external.mediators.ByGenreRemoteMediator
import com.challenge.instantflix.core.data.external.mediators.PopularRemoteMediator
import com.challenge.instantflix.core.data.external.mediators.TopRatedRemoteMediator
import com.challenge.instantflix.core.data.external.mediators.TrendingMoviesSeriesRemoteMediator
import com.challenge.instantflix.core.data.external.repository.RemoteRepository
import com.challenge.instantflix.core.data.internal.repository.LocalDataRepository
import com.challenge.instantflix.core.data.model.MovieTvEntity
import com.challenge.instantflix.core.data.model.RequestCategory
import com.challenge.instantflix.core.data.model.TypeRequest
import com.challenge.instantflix.core.utils.GenresCode
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@ExperimentalPagingApi
@Module
@InstallIn(SingletonComponent::class)
object PagersMediatorsModule {

    @Provides
    @Singleton
    fun providesTrendingMoviesAndTvShowsPager(
        localDataRepository: LocalDataRepository,
        remoteRepository: RemoteRepository,
    ): Pager<Int, MovieTvEntity> {
        return Pager(
            config = PagingConfig(20),
            remoteMediator = TrendingMoviesSeriesRemoteMediator(
                localDataRepository = localDataRepository,
                remoteRepository = remoteRepository,
                typeRequest = TypeRequest.ALL,
            ),
            pagingSourceFactory = {
                localDataRepository.getMoviesAndTvShowsByCategory(RequestCategory.TRENDING)
            },
        )
    }

    @Provides
    @Singleton
    fun providesTrendingMoviesPager(
        localDataRepository: LocalDataRepository,
        remoteRepository: RemoteRepository,
    ): Pager<Int, MovieTvEntity> {
        return Pager(
            config = PagingConfig(20),
            remoteMediator = TrendingMoviesSeriesRemoteMediator(
                localDataRepository = localDataRepository,
                remoteRepository = remoteRepository,
                typeRequest = TypeRequest.MOVIE,
            ),
            pagingSourceFactory = {
                localDataRepository.getMoviesOrTvShoesByCategoryAndType(
                    requestCategory = RequestCategory.TRENDING,
                    typeRequest = TypeRequest.MOVIE,
                )
            },
        )
    }

    @Provides
    @Singleton
    fun providesTrendingTvShowsPager(
        localDataRepository: LocalDataRepository,
        remoteRepository: RemoteRepository,
    ): Pager<Int, MovieTvEntity> {
        return Pager(
            config = PagingConfig(20),
            remoteMediator = TrendingMoviesSeriesRemoteMediator(
                localDataRepository = localDataRepository,
                remoteRepository = remoteRepository,
                typeRequest = TypeRequest.TV_SHOW,
            ),
            pagingSourceFactory = {
                localDataRepository.getMoviesOrTvShoesByCategoryAndType(
                    requestCategory = RequestCategory.TRENDING,
                    typeRequest = TypeRequest.TV_SHOW,
                )
            },
        )
    }

    @Provides
    @Singleton
    fun providesPopularMovies(
        localDataRepository: LocalDataRepository,
        remoteRepository: RemoteRepository,
    ): Pager<Int, MovieTvEntity> {
        return Pager(
            config = PagingConfig(20),
            remoteMediator = PopularRemoteMediator(
                localDataRepository = localDataRepository,
                remoteRepository = remoteRepository,
                typeRequest = TypeRequest.MOVIE,
            ),
            pagingSourceFactory = {
                localDataRepository.getMoviesOrTvShoesByCategoryAndType(
                    requestCategory = RequestCategory.POPULAR,
                    typeRequest = TypeRequest.MOVIE,
                )
            },
        )
    }

    @Provides
    @Singleton
    fun providesPopularTvShows(
        localDataRepository: LocalDataRepository,
        remoteRepository: RemoteRepository,
    ): Pager<Int, MovieTvEntity> {
        return Pager(
            config = PagingConfig(20),
            remoteMediator = PopularRemoteMediator(
                localDataRepository = localDataRepository,
                remoteRepository = remoteRepository,
                typeRequest = TypeRequest.TV_SHOW,
            ),
            pagingSourceFactory = {
                localDataRepository.getMoviesOrTvShoesByCategoryAndType(
                    requestCategory = RequestCategory.POPULAR,
                    typeRequest = TypeRequest.TV_SHOW,
                )
            },
        )
    }

    @Provides
    @Singleton
    fun providesTopRatedMovies(
        localDataRepository: LocalDataRepository,
        remoteRepository: RemoteRepository,
    ): Pager<Int, MovieTvEntity> {
        return Pager(
            config = PagingConfig(20),
            remoteMediator = TopRatedRemoteMediator(
                localDataRepository = localDataRepository,
                remoteRepository = remoteRepository,
                typeRequest = TypeRequest.MOVIE,
            ),
            pagingSourceFactory = {
                localDataRepository.getMoviesOrTvShoesByCategoryAndType(
                    requestCategory = RequestCategory.TOP_RATED,
                    typeRequest = TypeRequest.MOVIE,
                )
            },
        )
    }

    @Provides
    @Singleton
    fun providesTopRatedTvShows(
        localDataRepository: LocalDataRepository,
        remoteRepository: RemoteRepository,
    ): Pager<Int, MovieTvEntity> {
        return Pager(
            config = PagingConfig(20),
            remoteMediator = TopRatedRemoteMediator(
                localDataRepository = localDataRepository,
                remoteRepository = remoteRepository,
                typeRequest = TypeRequest.TV_SHOW,
            ),
            pagingSourceFactory = {
                localDataRepository.getMoviesOrTvShoesByCategoryAndType(
                    requestCategory = RequestCategory.TOP_RATED,
                    typeRequest = TypeRequest.TV_SHOW,
                )
            },
        )
    }

    @Provides
    @Singleton
    fun providesActionMovies(
        localDataRepository: LocalDataRepository,
        remoteRepository: RemoteRepository,
    ): Pager<Int, MovieTvEntity> {
        return Pager(
            config = PagingConfig(20),
            remoteMediator = ByGenreRemoteMediator(
                localDataRepository = localDataRepository,
                remoteRepository = remoteRepository,
                typeRequest = TypeRequest.MOVIE,
                genreId = GenresCode.ACTION_MOVIE,
            ),
            pagingSourceFactory = {
                localDataRepository.getMoviesOrTvShowsByGenreAndType(
                    genreId = GenresCode.ACTION_MOVIE,
                    typeRequest = TypeRequest.MOVIE,
                )
            },
        )
    }

    @Provides
    @Singleton
    fun providesActionAdventureTvShows(
        localDataRepository: LocalDataRepository,
        remoteRepository: RemoteRepository,
    ): Pager<Int, MovieTvEntity> {
        return Pager(
            config = PagingConfig(20),
            remoteMediator = ByGenreRemoteMediator(
                localDataRepository = localDataRepository,
                remoteRepository = remoteRepository,
                typeRequest = TypeRequest.TV_SHOW,
                genreId = GenresCode.ACTION_ADVENTURE_TV,
            ),
            pagingSourceFactory = {
                localDataRepository.getMoviesOrTvShowsByGenreAndType(
                    genreId = GenresCode.ACTION_ADVENTURE_TV,
                    typeRequest = TypeRequest.TV_SHOW,
                )
            },
        )
    }

    @Provides
    @Singleton
    fun providesComedyMovie(
        localDataRepository: LocalDataRepository,
        remoteRepository: RemoteRepository,
    ): Pager<Int, MovieTvEntity> {
        return Pager(
            config = PagingConfig(20),
            remoteMediator = ByGenreRemoteMediator(
                localDataRepository = localDataRepository,
                remoteRepository = remoteRepository,
                typeRequest = TypeRequest.MOVIE,
                genreId = GenresCode.COMEDY_MOVIE_TV,
            ),
            pagingSourceFactory = {
                localDataRepository.getMoviesOrTvShowsByGenreAndType(
                    genreId = GenresCode.COMEDY_MOVIE_TV,
                    typeRequest = TypeRequest.MOVIE,
                )
            },
        )
    }

    @Provides
    @Singleton
    fun providesComedyTvShow(
        localDataRepository: LocalDataRepository,
        remoteRepository: RemoteRepository,
    ): Pager<Int, MovieTvEntity> {
        return Pager(
            config = PagingConfig(20),
            remoteMediator = ByGenreRemoteMediator(
                localDataRepository = localDataRepository,
                remoteRepository = remoteRepository,
                typeRequest = TypeRequest.TV_SHOW,
                genreId = GenresCode.COMEDY_MOVIE_TV,
            ),
            pagingSourceFactory = {
                localDataRepository.getMoviesOrTvShowsByGenreAndType(
                    genreId = GenresCode.COMEDY_MOVIE_TV,
                    typeRequest = TypeRequest.TV_SHOW,
                )
            },
        )
    }
}
