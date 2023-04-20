package com.challenge.instantflix.core.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class MovieTv(
    @PrimaryKey val id: Int,
    val title: String?,
    val overview: String?,
    val popularity: Double?,
    @SerializedName("vote_average")
    val voteAverage: Double?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
)

fun MovieTv.getImageBackDrop(): String {
    return "https://www.themoviedb.org/t/p/w600_and_h900_bestv2${this.backdropPath ?: ""}"
}

fun MovieTv.getImagePoster(): String {
    return "https://www.themoviedb.org/t/p/w600_and_h900_bestv2${this.posterPath ?: ""}"
}
