package com.theblackbit.instantflix.core.data.model

import com.google.gson.annotations.SerializedName

data class MovieTv(
    val id: Int,
    val title: String?,
    val name: String? = null,
    val overview: String?,
    val popularity: Double?,
    @SerializedName("genre_ids")
    val genreIds: List<Int>,
    @SerializedName("vote_average")
    val voteAverage: Double?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
)

/**
 *Converts a Movie or TV Show object to a MovieTvEntity object.
 *@param genreIdRequest the ID of the requested genre (optional)
 *@param requestCategory the [RequestCategory]
 *@param typeRequest the [TypeRequest]
 *@param page the page number of the request
 *@param totalResult the total number of results for the request
 *@return a MovieTvEntity object containing the relevant information from the input object
 */
fun MovieTv.toMovieTvEntity(
    genreIdRequest: Int? = null,
    requestCategory: RequestCategory,
    typeRequest: TypeRequest,
    page: Int?,
    totalResult: Int?,
    genres: List<String>,
): MovieTvEntity {
    return MovieTvEntity(
        id = id,
        title = title ?: name,
        overview = overview,
        popularity = popularity,
        genres = genres,
        voteAverage = voteAverage,
        releaseDate = releaseDate,
        posterPath = posterPath,
        backdropPath = backdropPath,
        genreIdRequested = genreIdRequest,
        requestCategory = requestCategory,
        typeRequest = typeRequest,
        page = page,
        totalResults = totalResult,
    )
}
