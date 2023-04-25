package com.challenge.instantflix.core.di

import com.challenge.instantflix.BuildConfig
import com.challenge.instantflix.core.data.external.datasource.TheMovieDBApi
import com.google.gson.GsonBuilder
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataSourceModule {

    @Singleton
    @Provides
    fun providesRetrofitDataSource(): TheMovieDBApi {
        val builder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            // Interceptor that listen every request just in debug
            builder.addInterceptor(OkHttpProfilerInterceptor())
        }

        builder.addInterceptor {
            val originalRequest = it.request()
            val originalHttpUrl = originalRequest.url
            val url = originalHttpUrl.newBuilder()
                .addQueryParameter("api_key", BuildConfig.API_KEY)
                .addQueryParameter("language", "en-US")
                .build()

            val newRequest = originalRequest.newBuilder()
                .header("Content-Type", "application/json;charset=utf-8")
                .method(originalRequest.method, originalRequest.body)
                .url(url = url)
                .build()

            it.proceed(request = newRequest)
        }
        return Retrofit.Builder()
            .client(builder.build())
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(TheMovieDBApi::class.java)
    }
}
