package com.challenge.instantflix.core.data.external.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.challenge.instantflix.core.data.external.mediators.TrendingMoviesSeriesRemoteMediator
import com.challenge.instantflix.core.data.external.repository.RemoteRepository
import com.challenge.instantflix.core.data.internal.repository.LocalDataRepository
import com.challenge.instantflix.core.data.model.ErrorResponse
import com.challenge.instantflix.core.data.model.MovieTv
import com.challenge.instantflix.core.data.model.MovieTvEntity
import com.challenge.instantflix.core.data.model.MovieTvResponse
import com.challenge.instantflix.core.data.model.TypeRequest
import com.challenge.instantflix.core.utils.ApiResultHandle
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

@ExperimentalPagingApi
class TrendingMoviesSeriesRemoteMediatorTest {

    private val localDataRepository = mockk<LocalDataRepository>()
    private val remoteRepository = mockk<RemoteRepository>()
    private val pagingState = mockk<PagingState<Int, MovieTvEntity>>()
    private val movieTvMock = mockk<MovieTv>(relaxed = true)
    private lateinit var trendingMoviesSeriesRemoteMediator: TrendingMoviesSeriesRemoteMediator

    @Before
    fun setUp() {
        trendingMoviesSeriesRemoteMediator = TrendingMoviesSeriesRemoteMediator(
            localDataRepository = localDataRepository,
            remoteRepository = remoteRepository,
            typeRequest = TypeRequest.MOVIE,
        )
    }

    @Test
    fun `load remote data and save to database`(): Unit = runBlocking {
        val listResult = listOf(movieTvMock, movieTvMock, movieTvMock)
        val expectedResponse = ApiResultHandle.Success(
            MovieTvResponse(
                page = 1,
                result = listResult,
                totalPages = 1,
                totalResults = 3,
            ),
        )
        coJustRun { localDataRepository.clearAll() }
        coJustRun { localDataRepository.upsertMovieOrTvCached(any()) }

        coEvery { remoteRepository.requestTrendingMoviesSeries(typeRequest = TypeRequest.MOVIE) } returns expectedResponse

        val result = trendingMoviesSeriesRemoteMediator.load(LoadType.REFRESH, state = pagingState)

        assertTrue(result is RemoteMediator.MediatorResult.Success)

        coVerify { localDataRepository.upsertMovieOrTvCached(any()) }
        coVerify { localDataRepository.clearAll() }
    }

    @Test
    fun `load remote data and save to database and not clearData`(): Unit = runBlocking {
        val listResult = listOf(movieTvMock, movieTvMock, movieTvMock)
        val expectedResponse = ApiResultHandle.Success(
            MovieTvResponse(
                page = 1,
                result = listResult,
                totalPages = 1,
                totalResults = 3,
            ),
        )
        coJustRun { localDataRepository.clearAll() }
        coJustRun { localDataRepository.upsertMovieOrTvCached(any()) }

        coEvery { remoteRepository.requestTrendingMoviesSeries(typeRequest = TypeRequest.MOVIE) } returns expectedResponse

        val result = trendingMoviesSeriesRemoteMediator.load(LoadType.APPEND, state = pagingState)

        assertTrue(result is RemoteMediator.MediatorResult.Success)

        coVerify { localDataRepository.upsertMovieOrTvCached(any()) }
        coVerify(exactly = 0) { localDataRepository.clearAll() }
    }

    @Test
    fun `return error when api error call fails`(): Unit = runBlocking {
        val expectedResponse = ApiResultHandle.ApiError(ErrorResponse())

        coEvery { remoteRepository.requestTrendingMoviesSeries(typeRequest = TypeRequest.MOVIE) } returns expectedResponse

        coJustRun { localDataRepository.clearAll() }
        coJustRun { localDataRepository.upsertMovieOrTvCached(any()) }

        val result = trendingMoviesSeriesRemoteMediator.load(LoadType.REFRESH, state = pagingState)

        assertTrue(result is RemoteMediator.MediatorResult.Error)

        coVerify(exactly = 0) { localDataRepository.upsertMovieOrTvCached(any()) }
        coVerify(exactly = 0) { localDataRepository.clearAll() }
    }

    @Test
    fun `return error when network fails`(): Unit = runBlocking {
        val expectedResponse = ApiResultHandle.NetworkError()

        coEvery { remoteRepository.requestTrendingMoviesSeries(typeRequest = TypeRequest.MOVIE) } returns expectedResponse

        coJustRun { localDataRepository.clearAll() }
        coJustRun { localDataRepository.upsertMovieOrTvCached(any()) }

        val result = trendingMoviesSeriesRemoteMediator.load(LoadType.REFRESH, state = pagingState)

        assertTrue(result is RemoteMediator.MediatorResult.Error)

        coVerify(exactly = 0) { localDataRepository.upsertMovieOrTvCached(any()) }
        coVerify(exactly = 0) { localDataRepository.clearAll() }
    }
}
