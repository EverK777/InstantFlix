package com.challenge.instantflix.core.data.model

import com.google.gson.annotations.SerializedName

data class MovieTv(
    val id: Int,
    val title: String?,
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
