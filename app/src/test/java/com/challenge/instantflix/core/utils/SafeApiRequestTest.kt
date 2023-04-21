package com.challenge.instantflix.core.utils

import com.challenge.instantflix.core.data.model.ErrorResponse
import com.challenge.instantflix.core.data.model.MovieTvResponse
import com.google.gson.Gson
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import java.io.IOException
import kotlin.jvm.Throws

class SafeApiRequestTest {

    private lateinit var safeApiRequest: SafeApiRequestImpl

    private lateinit var gson: Gson

    @Before
    fun setUp() {
        gson = mockk()
        safeApiRequest = SafeApiRequestImpl(gson)
    }

    @Test
    fun `safeApiRequest returns success result when apiRequest succeeds`() = runBlocking {
        val request = fakeRequest()
        val apiResultHandle = safeApiRequest.request(Dispatchers.IO) { fakeRequest() }
        assertTrue(apiResultHandle is ApiResultHandle.Success)
        assertEquals(request, (apiResultHandle as ApiResultHandle.Success).value)
    }

    @Test
    fun `safeApiRequest returns network error result when apiRequest throws IOException`() =
        runBlocking {
            val apiRequest: suspend () -> MovieTvResponse = { throw IOException() }

            val apiResultHandle = safeApiRequest.request(Dispatchers.IO, apiRequest)

            assertTrue(apiResultHandle is ApiResultHandle.NetworkError)
        }

    @Test
    fun `safeApiRequest returns api error result when apiRequest throws HttpException`() =
        runBlocking {
            val errorResponse = ErrorResponse()
            val httpException = fakeRequestError()

            val apiRequest: suspend () -> MovieTvResponse = { throw httpException }

            val apiResultHandle = safeApiRequest.request(Dispatchers.IO, apiRequest)

            assertTrue(apiResultHandle is ApiResultHandle.ApiError)
            assertEquals(errorResponse, (apiResultHandle as ApiResultHandle.ApiError).error)
        }

    @Test
    fun `safeApiRequest returns api error result when apiRequest throws unknown exception`() =
        runBlocking {
            val apiRequest: suspend () -> MovieTvResponse = { throw Exception() }

            val apiResultHandle = safeApiRequest.request(Dispatchers.IO, apiRequest)

            assertTrue(apiResultHandle is ApiResultHandle.ApiError)
            assertEquals(ErrorResponse(), (apiResultHandle as ApiResultHandle.ApiError).error)
        }

    private fun fakeRequest(): MovieTvResponse {
        return MovieTvResponse(page = 1, result = emptyList(), totalPages = 1, totalResults = 0)
    }

    @Throws(HttpException::class)
    private fun fakeRequestError(): HttpException {
        val json: String = "{\n" +
            "  \"status_message\": \"The resource you requested could not be found.\",\n" +
            "  \"status_code\": 36\n" +
            "}"
        return HttpException(
            retrofit2.Response.error<ResponseBody>(
                401,
                json.toResponseBody("json/text".toMediaTypeOrNull()),
            ),
        )
    }
}
