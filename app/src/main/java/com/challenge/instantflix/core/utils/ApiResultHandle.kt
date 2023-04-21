package com.challenge.instantflix.core.utils

import androidx.annotation.StringRes
import com.challenge.instantflix.R
import com.challenge.instantflix.core.data.model.ErrorResponse

sealed class ApiResultHandle<out T> {
    data class Success<out T>(val value: T) : ApiResultHandle<T>()
    data class ApiError(val error: ErrorResponse) : ApiResultHandle<Nothing>()
    data class NetworkError(@StringRes val errorNetworkMessage: Int = R.string.something_went_network_connection) :
        ApiResultHandle<Nothing>()
}
