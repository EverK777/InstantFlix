package com.challenge.instantflix.core.data.external.repository

import com.challenge.instantflix.core.data.external.datasource.TheMovieDBApi
import com.challenge.instantflix.core.data.model.ErrorResponse
import com.challenge.instantflix.core.data.model.GenreResponse
import com.challenge.instantflix.core.data.model.MovieTvResponse
import com.challenge.instantflix.core.utils.ApiResultHandle
import com.challenge.instantflix.core.utils.SafeApiRequest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class TheMovieDbRepositoryTest {

    private lateinit var theMovieDbDataSource: TheMovieDbRepository

    private val safeApiRequest: SafeApiRequestTest = SafeApiRequestTest()

    private lateinit var theMovieDBApi: TheMovieDBApi

    private val testCoroutineDispatcher = StandardTestDispatcher()

    private val movieTvResponse = MovieTvResponse(
        page = 1,
        result = emptyList(),
        totalPages = 1,
        totalResults = 1,
    )

    private val genreResponse = GenreResponse(emptyList())

    @Before
    fun setUp() {
        theMovieDBApi = mockk()
        theMovieDbDataSource =
            TheMovieDbRepository(safeApiRequest, theMovieDBApi, testCoroutineDispatcher)
    }

    @Test
    fun `requestTrendingMoviesSeries should return correct response when API call is successful`() =
        runTest {
            safeApiRequest.setRequestState(ApiRequestStateTest.SUCCESS)
            coEvery {
                theMovieDBApi.requestTrendingMoviesSeries()
            } returns movieTvResponse

            val result = theMovieDbDataSource.requestTrendingMoviesSeries()

            assertEquals((result as ApiResultHandle.Success).value, movieTvResponse)

            coVerify { theMovieDBApi.requestTrendingMoviesSeries() }
        }

    @Test
    fun `requestTrendingMoviesSeries returns apiError`() =
        runTest {
            safeApiRequest.setRequestState(ApiRequestStateTest.ERROR)
            val result = theMovieDbDataSource.requestTrendingMoviesSeries()
            assertTrue(result is ApiResultHandle.ApiError)
        }

    @Test
    fun `requestTrendingMoviesSeries should returns networkError`() =
        runTest {
            safeApiRequest.setRequestState(ApiRequestStateTest.NETWORK_ERROR)
            val result = theMovieDbDataSource.requestTrendingMoviesSeries()
            assertTrue(result is ApiResultHandle.NetworkError)
        }

    @Test
    fun `requestTopRated should return correct response when API call is successful`() =
        runTest {
            safeApiRequest.setRequestState(ApiRequestStateTest.SUCCESS)
            coEvery {
                theMovieDBApi.requestTopRated("movie", 1, "Test")
            } returns movieTvResponse

            val result = theMovieDbDataSource.requestTopRated("movie", 1, "Test")

            assertEquals((result as ApiResultHandle.Success).value, movieTvResponse)

            coVerify { theMovieDBApi.requestTopRated("movie", 1, "Test") }
        }

    @Test
    fun `requestTopRatedMovies returns apiError`() =
        runTest {
            safeApiRequest.setRequestState(ApiRequestStateTest.ERROR)
            val result = theMovieDbDataSource.requestTopRated("movie", 1, "Test")

            assertTrue(result is ApiResultHandle.ApiError)
        }

    @Test
    fun `requestTopRatedMovies should returns networkError`() =
        runTest {
            safeApiRequest.setRequestState(ApiRequestStateTest.NETWORK_ERROR)
            val result = theMovieDbDataSource.requestTopRated("movie", 1, "Test")

            assertTrue(result is ApiResultHandle.NetworkError)
        }

    @Test
    fun `requestPopular should return correct response when API call is successful`() =
        runTest {
            safeApiRequest.setRequestState(ApiRequestStateTest.SUCCESS)
            coEvery {
                theMovieDBApi.requestPopular("movie", 1)
            } returns movieTvResponse

            val result = theMovieDbDataSource.requestPopular("movie", 1)

            assertEquals((result as ApiResultHandle.Success).value, movieTvResponse)

            coVerify { theMovieDBApi.requestPopular("movie", 1) }
        }

    @Test
    fun `requestPopular returns apiError`() =
        runTest {
            safeApiRequest.setRequestState(ApiRequestStateTest.ERROR)
            val result = theMovieDbDataSource.requestPopular("movie", 1)
            assertTrue(result is ApiResultHandle.ApiError)
        }

    @Test
    fun `requestPopular should returns networkError`() =
        runTest {
            safeApiRequest.setRequestState(ApiRequestStateTest.NETWORK_ERROR)
            val result = theMovieDbDataSource.requestPopular("movie", 1)
            assertTrue(result is ApiResultHandle.NetworkError)
        }

    @Test
    fun `requestByGenre should return correct response when API call is successful`() =
        runTest {
            safeApiRequest.setRequestState(ApiRequestStateTest.SUCCESS)
            coEvery {
                theMovieDBApi.requestByGenre("movie", 1, 1)
            } returns movieTvResponse

            val result = theMovieDbDataSource.requestByGenre("movie", 1, 1)

            assertEquals((result as ApiResultHandle.Success).value, movieTvResponse)

            coVerify { theMovieDBApi.requestByGenre("movie", 1, 1) }
        }

    @Test
    fun `requestByGenre returns apiError`() =
        runTest {
            safeApiRequest.setRequestState(ApiRequestStateTest.ERROR)
            val result = theMovieDbDataSource.requestByGenre("movie", 1, 1)
            assertTrue(result is ApiResultHandle.ApiError)
        }

    @Test
    fun `requestByGenre should returns networkError`() =
        runTest {
            safeApiRequest.setRequestState(ApiRequestStateTest.NETWORK_ERROR)
            val result = theMovieDbDataSource.requestByGenre("movie", 1, 1)
            assertTrue(result is ApiResultHandle.NetworkError)
        }

    @Test
    fun `requestGenres should return correct response when API call is successful`() =
        runTest {
            safeApiRequest.setRequestState(ApiRequestStateTest.SUCCESS)
            coEvery {
                theMovieDBApi.requestGenre("movie")
            } returns genreResponse

            val result = theMovieDbDataSource.requestGenres("movie")

            assertEquals((result as ApiResultHandle.Success).value, genreResponse)

            coVerify { theMovieDBApi.requestGenre("movie") }
        }

    @Test
    fun `requestGenres returns apiError`() =
        runTest {
            safeApiRequest.setRequestState(ApiRequestStateTest.ERROR)
            val result = theMovieDbDataSource.requestGenres("movie")
            assertTrue(result is ApiResultHandle.ApiError)
        }

    @Test
    fun `requestGenres should returns networkError`() =
        runTest {
            safeApiRequest.setRequestState(ApiRequestStateTest.NETWORK_ERROR)
            val result = theMovieDbDataSource.requestGenres("movie")
            assertTrue(result is ApiResultHandle.NetworkError)
        }

    /**
     * Class that mocks a safeApiRequest in order to simulate Api Responses
     */
    inner class SafeApiRequestTest : SafeApiRequest {

        private var apiRequestStateTest: ApiRequestStateTest = ApiRequestStateTest.SUCCESS

        fun setRequestState(apiRequestStateTest: ApiRequestStateTest) {
            this.apiRequestStateTest = apiRequestStateTest
        }

        override suspend fun <T> request(
            dispatcher: CoroutineDispatcher,
            apiRequest: suspend () -> T,
        ): ApiResultHandle<T> {
            return when (apiRequestStateTest) {
                ApiRequestStateTest.SUCCESS -> ApiResultHandle.Success(apiRequest.invoke())
                ApiRequestStateTest.ERROR -> ApiResultHandle.ApiError(error = ErrorResponse())
                ApiRequestStateTest.NETWORK_ERROR -> ApiResultHandle.NetworkError()
            }
        }
    }
}

enum class ApiRequestStateTest {
    SUCCESS, ERROR, NETWORK_ERROR
}
