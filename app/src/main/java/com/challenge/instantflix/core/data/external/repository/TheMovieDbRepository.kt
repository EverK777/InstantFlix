package com.challenge.instantflix.core.data.external.repository

import com.challenge.instantflix.core.data.external.datasource.TheMovieDBApi
import com.challenge.instantflix.core.data.model.GenreResponse
import com.challenge.instantflix.core.data.model.MovieTvResponse
import com.challenge.instantflix.core.utils.ApiResultHandle
import com.challenge.instantflix.core.utils.SafeApiRequest
import kotlinx.coroutines.CoroutineDispatcher

/**
 * Class that implements the RemoteRepository interface and provides methods for requesting movie and
 * TV show data from the remote data source using the MovieDB API.
 * @param safeApiRequest An instance of the SafeApiRequest class used to make safe API requests.
 * @param theMovieDBApi An instance of the TheMovieDBApi interface used to make requests to the MovieDB API.
 * @param coroutineDispatcher A CoroutineDispatcher object dispatcher for the coroutines used in the class.
 */
class TheMovieDbRepository(
    private val safeApiRequest: SafeApiRequest,
    private val theMovieDBApi: TheMovieDBApi,
    private val coroutineDispatcher: CoroutineDispatcher,
) : RemoteRepository {

    /**
     * Requests the trending movies and TV series from the MovieDB API.
     * @return An ApiResultHandle<MovieTvResponse> object representing the result of the request.
     */
    override suspend fun requestTrendingMoviesSeries(): ApiResultHandle<MovieTvResponse> {
        return safeApiRequest.request(coroutineDispatcher) { theMovieDBApi.requestTrendingMoviesSeries() }
    }

    /**
     * Requests the top rated movies from the MovieDB API.
     * @param type indicates if it is movie or tv
     * @param page The page number of the results to retrieve.
     * @param sortBy The sorting option for the results.
     * @return An ApiResultHandle<MovieTvResponse> object representing the result of the request.
     */
    override suspend fun requestTopRated(
        type: String,
        page: Int,
        sortBy: String,
    ): ApiResultHandle<MovieTvResponse> {
        return safeApiRequest.request(coroutineDispatcher) {
            theMovieDBApi.requestTopRated(
                type = type,
                page = page,
                sortBy = sortBy,
            )
        }
    }

    /**
     * Requests the popular movies from the MovieDB API.
     * @param type indicates if it is movie or tv
     * @param page The page number of the results to retrieve.
     * @return An ApiResultHandle<MovieTvResponse> object representing the result of the request.
     */
    override suspend fun requestPopular(type: String, page: Int): ApiResultHandle<MovieTvResponse> {
        return safeApiRequest.request(coroutineDispatcher) {
            theMovieDBApi.requestPopular(
                type = type,
                page = page,
            )
        }
    }

    /**
     * Requests the movies by genre from the MovieDB API.
     * @param type indicates if it is movie or tv
     * @param page The page number of the results to retrieve.
     * @param genreId The ID of the genre to retrieve results for.
     * @return An ApiResultHandle<MovieTvResponse> object representing the result of the request.
     */
    override suspend fun requestByGenre(
        type: String,
        page: Int,
        genreId: Int,
    ): ApiResultHandle<MovieTvResponse> {
        return safeApiRequest.request(coroutineDispatcher) {
            theMovieDBApi.requestByGenre(
                type = type,
                page = page,
                genreId = genreId,
            )
        }
    }

    /**
     *Requests the list of movie genres from the MovieDB API.
     *@param type indicates if it is movie or tv
     *@return An [ApiResultHandle] object containing the response data or error message.
     */
    override suspend fun requestGenres(type: String): ApiResultHandle<GenreResponse> {
        return safeApiRequest.request(coroutineDispatcher) { theMovieDBApi.requestGenre(type) }
    }
}
