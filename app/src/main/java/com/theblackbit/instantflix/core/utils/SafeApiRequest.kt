package com.theblackbit.instantflix.core.utils

import kotlinx.coroutines.CoroutineDispatcher

interface SafeApiRequest {
    suspend fun <T> request(
        dispatcher: CoroutineDispatcher,
        apiRequest: suspend () -> T,
    ): ApiResultHandle<T>
}
