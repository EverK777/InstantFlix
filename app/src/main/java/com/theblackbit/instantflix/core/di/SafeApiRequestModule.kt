package com.theblackbit.instantflix.core.di

import com.google.gson.Gson
import com.theblackbit.instantflix.core.utils.SafeApiRequest
import com.theblackbit.instantflix.core.utils.SafeApiRequestImpl
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
