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
 * Returns the URL for the movie or TV show's backdrop image, if available.
 * The URL follows the format "https://www.themoviedb.org/t/p/w600_and_h900_bestv2" followed
 * by the backdrop path, or an empty string if the backdrop path is null or empty.
 *
 * @return The URL for the movie or TV show's backdrop image.
 */
fun MovieTv.getImageBackDrop(): String {
    return "https://www.themoviedb.org/t/p/w600_and_h900_bestv2${this.backdropPath ?: ""}"
}

/**
 * Returns the URL for the movie or TV show's poster image, if available.
 * The URL follows the format "https://www.themoviedb.org/t/p/w600_and_h900_bestv2" followed
 * by the poster path, or an empty string if the poster path is null or empty.
 *
 * @return The URL for the movie or TV show's poster image.
 */
fun MovieTv.getImagePoster(): String {
    return "https://www.themoviedb.org/t/p/w600_and_h900_bestv2${this.posterPath ?: ""}"
}
