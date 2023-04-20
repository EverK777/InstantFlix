package com.challenge.instantflix.core.data.external.datasource

import com.challenge.instantflix.core.data.model.MovieTvResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TheMovieDBApi {

    @GET("trending/all/week")
    suspend fun requestTrendingMoviesSeries(): MovieTvResponse

    @GET("movie/top_rated")
    suspend fun requestTopRatedMovies(
        @Query("page") page: Int,
        @Query("sort_by") sortBy: String,
    ): MovieTvResponse

    @GET("movie/popular")
    suspend fun requestPopularMovies(
        @Query("page") page: Int,
    )

    @GET("discover/movie")
    suspend fun requestMoviesByGenre(
        @Query("page") page: Int,
        @Query("with_genres") genreId: Int,
    ): MovieTvResponse

    @GET("tv/top_rated")
    suspend fun requestTopRatedSeries(): MovieTvResponse

    @GET("tv/popular")
    suspend fun requestPopularSeries()

    @GET("discover/tv")
    suspend fun requestTvShowsByGenre(
        @Query("page") page: Int,
        @Query("with_genres") genreId: Int,
    ): MovieTvResponse
}
