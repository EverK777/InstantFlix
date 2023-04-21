package com.challenge.instantflix.core.di

import com.challenge.instantflix.core.utils.SafeApiRequest
import com.challenge.instantflix.core.utils.SafeApiRequestImpl
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SafeApiRequestModule {

    @Provides
    @Singleton
    fun providesSafeApiRequest(): SafeApiRequest {
        val gson = Gson()
        return SafeApiRequestImpl(gson)
    }
}
