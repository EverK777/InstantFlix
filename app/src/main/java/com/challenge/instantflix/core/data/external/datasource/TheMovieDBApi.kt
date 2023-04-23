package com.challenge.instantflix.core.data.external.datasource

import com.challenge.instantflix.core.data.model.GenreResponse
import com.challenge.instantflix.core.data.model.MovieTvResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDBApi {

    @GET("trending/{type}/week")
    suspend fun requestTrendingMoviesSeries(@Path("type") type: String): MovieTvResponse

    @GET("{type}/top_rated")
    suspend fun requestTopRated(
        @Path("type") type: String,
        @Query("page") page: Int,
        @Query("sort_by") sortBy: String,
    ): MovieTvResponse

    @GET("{type}/popular")
    suspend fun requestPopular(
        @Path("type") type: String,
        @Query("page") page: Int,
    ): MovieTvResponse

    @GET("discover/{type}")
    suspend fun requestByGenre(
        @Path("type") type: String,
        @Query("page") page: Int,
        @Query("with_genres") genreId: Int,
    ): MovieTvResponse

    @GET("genre/{type}")
    suspend fun requestGenre(
        @Path("type") type: String,
    ): GenreResponse
}
