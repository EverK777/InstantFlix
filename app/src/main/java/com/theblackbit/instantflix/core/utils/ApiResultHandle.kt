package com.theblackbit.instantflix.core.utils

import androidx.annotation.StringRes
import com.theblackbit.instantflix.R
import com.theblackbit.instantflix.core.data.model.ErrorResponse

sealed class ApiResultHandle<out T> {
    data class Success<out T>(val value: T) : ApiResultHandle<T>()
    data class ApiError(val error: ErrorResponse) : ApiResultHandle<Nothing>()
    data class NetworkError(@StringRes val errorNetworkMessage: Int = R.string.something_went_network_connection) :
        ApiResultHandle<Nothing>()
}
