package com.challenge.instantflix.core.data.external.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.challenge.instantflix.core.data.external.mediators.ByGenreRemoteMediator
import com.challenge.instantflix.core.data.external.repository.RemoteRepository
import com.challenge.instantflix.core.data.internal.repository.LocalDataRepository
import com.challenge.instantflix.core.data.model.ErrorResponse
import com.challenge.instantflix.core.data.model.MovieTv
import com.challenge.instantflix.core.data.model.MovieTvEntity
import com.challenge.instantflix.core.data.model.MovieTvResponse
import com.challenge.instantflix.core.data.model.RequestCategory
import com.challenge.instantflix.core.data.model.TypeRequest
import com.challenge.instantflix.core.utils.ApiResultHandle
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

@ExperimentalPagingApi
class ByGenreRemoteMediatorTest {
    private val localDataRepository = mockk<LocalDataRepository>()
    private val remoteRepository = mockk<RemoteRepository>()
    private val pagingState = mockk<PagingState<Int, MovieTvEntity>>()
    private val movieTvMock = mockk<MovieTv>(relaxed = true)
    private lateinit var byGenreRemoteMediator: ByGenreRemoteMediator

    @Before
    fun setUp() {
        byGenreRemoteMediator = ByGenreRemoteMediator(
            localDataRepository = localDataRepository,
            remoteRepository = remoteRepository,
            typeRequest = TypeRequest.MOVIE,
            genreId = 1,
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

        coEvery {
            remoteRepository.requestByGenre(
                type = TypeRequest.MOVIE.type,
                page = 1,
                genreId = 1,
            )
        } returns expectedResponse

        val result = byGenreRemoteMediator.load(LoadType.REFRESH, state = pagingState)

        TestCase.assertTrue(result is RemoteMediator.MediatorResult.Success)

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
        every { pagingState.lastItemOrNull() } returns null

        coEvery {
            remoteRepository.requestByGenre(
                type = TypeRequest.MOVIE.type,
                page = 1,
                genreId = 1,
            )
        } returns expectedResponse

        val result = byGenreRemoteMediator.load(LoadType.APPEND, state = pagingState)

        TestCase.assertTrue(result is RemoteMediator.MediatorResult.Success)

        coVerify { localDataRepository.upsertMovieOrTvCached(any()) }
        coVerify(exactly = 0) { localDataRepository.clearAll() }
    }

    @Test
    fun `append data request second page`(): Unit = runBlocking {
        val listResult = listOf(movieTvMock, movieTvMock, movieTvMock)
        val expectedResponse = ApiResultHandle.Success(
            MovieTvResponse(
                page = 2,
                result = listResult,
                totalPages = 3,
                totalResults = 3,
            ),
        )
        coJustRun { localDataRepository.clearAll() }
        coJustRun { localDataRepository.upsertMovieOrTvCached(any()) }
        every { pagingState.lastItemOrNull() } returns MovieTvEntity(
            id = 1,
            null,
            null,
            null,
            emptyList(),
            null,
            null,
            null,
            null,
            null,
            RequestCategory.POPULAR,
            TypeRequest.MOVIE,
            1,
            totalResults = 3,
        )

        coEvery {
            remoteRepository.requestByGenre(
                type = TypeRequest.MOVIE.type,
                page = 2,
                genreId = 1,
            )
        } returns expectedResponse

        val result = byGenreRemoteMediator.load(LoadType.APPEND, state = pagingState)

        TestCase.assertTrue(result is RemoteMediator.MediatorResult.Success)

        coVerify { localDataRepository.upsertMovieOrTvCached(any()) }
        coVerify(exactly = 0) { localDataRepository.clearAll() }
    }

    @Test
    fun `return error when api error call fails`(): Unit = runBlocking {
        val expectedResponse = ApiResultHandle.ApiError(ErrorResponse())

        coEvery {
            remoteRepository.requestByGenre(
                type = TypeRequest.MOVIE.type,
                page = 1,
                genreId = 1,
            )
        } returns expectedResponse

        coJustRun { localDataRepository.clearAll() }
        coJustRun { localDataRepository.upsertMovieOrTvCached(any()) }

        val result = byGenreRemoteMediator.load(LoadType.REFRESH, state = pagingState)

        TestCase.assertTrue(result is RemoteMediator.MediatorResult.Error)

        coVerify(exactly = 0) { localDataRepository.upsertMovieOrTvCached(any()) }
        coVerify(exactly = 0) { localDataRepository.clearAll() }
    }

    @Test
    fun `return error when network error call fails`(): Unit = runBlocking {
        val expectedResponse = ApiResultHandle.NetworkError()

        coEvery {
            remoteRepository.requestByGenre(
                type = TypeRequest.MOVIE.type,
                page = 1,
                genreId = 1,
            )
        } returns expectedResponse

        coJustRun { localDataRepository.clearAll() }
        coJustRun { localDataRepository.upsertMovieOrTvCached(any()) }

        val result = byGenreRemoteMediator.load(LoadType.REFRESH, state = pagingState)

        TestCase.assertTrue(result is RemoteMediator.MediatorResult.Error)

        coVerify(exactly = 0) { localDataRepository.upsertMovieOrTvCached(any()) }
        coVerify(exactly = 0) { localDataRepository.clearAll() }
    }
}
