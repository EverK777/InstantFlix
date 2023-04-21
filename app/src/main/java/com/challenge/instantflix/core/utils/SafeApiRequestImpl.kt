package com.challenge.instantflix.core.utils

import com.challenge.instantflix.core.data.model.ErrorResponse
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

/**
 * Implementation of [SafeApiRequest] interface that provides a safe way to execute an API request.
 *
 * @property gson An instance of [Gson] used to convert the error response body to the [ErrorResponse] object.
 */
class SafeApiRequestImpl(
    private val gson: Gson,
) : SafeApiRequest {

    /**
     * Executes the API request safely and returns the result wrapped in an [ApiResultHandle] object.
     *
     * @param dispatcher A [CoroutineDispatcher] used to switch to a background thread before executing the API request.
     * @param apiRequest A suspend function that performs the API request and returns the result.
     * @return An [ApiResultHandle] object that contains either the success result or the error result.
     */
    override suspend fun <T> request(
        dispatcher: CoroutineDispatcher,
        apiRequest: suspend () -> T,
    ): ApiResultHandle<T> {
        return withContext(dispatcher) {
            try {
                ApiResultHandle.Success(apiRequest.invoke())
            } catch (throwable: Throwable) {
                val generalErrorResponse = ErrorResponse()
                when (throwable) {
                    is IOException -> ApiResultHandle.NetworkError()
                    is HttpException -> {
                        val errorResponse = convertErrorBody(throwable)
                        ApiResultHandle.ApiError(errorResponse ?: generalErrorResponse)
                    }

                    else -> {
                        ApiResultHandle.ApiError(error = generalErrorResponse)
                    }
                }
            }
        }
    }

    /**
     * Converts the error response body of the [HttpException] to the [ErrorResponse] object.
     *
     * @param throwable An instance of [HttpException] containing the error response body.
     * @return An [ErrorResponse] object representing the error response body, or null if it cannot be converted.
     */
    private fun convertErrorBody(throwable: HttpException): ErrorResponse? {
        return try {
            throwable.response()?.errorBody()?.source()?.let {
                gson.fromJson(it.readUtf8(), ErrorResponse::class.java)
            }
        } catch (exception: Exception) {
            ErrorResponse()
        }
    }
}
