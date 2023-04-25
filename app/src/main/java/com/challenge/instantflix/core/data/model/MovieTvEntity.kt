package com.challenge.instantflix.core.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieTvEntity(
    @PrimaryKey val id: Int,
    val title: String?,
    val overview: String?,
    val popularity: Double?,
    val genres: List<String>,
    val voteAverage: Double?,
    val releaseDate: String?,
    val posterPath: String?,
    val backdropPath: String?,
    val genreIdRequested: Int? = null,
    val requestCategory: RequestCategory,
    val typeRequest: TypeRequest,
    val page: Int?,
    val totalResults: Int?,
)

/**
 * Returns the URL for the movie or TV show's backdrop image, if available.
 * The URL follows the format "https://www.themoviedb.org/t/p/w600_and_h900_bestv2" followed
 * by the backdrop path, or an empty string if the backdrop path is null or empty.
 *
 * @return The URL for the movie or TV show's backdrop image.
 */
fun MovieTvEntity.getImageBackDrop(): String {
    return "https://www.themoviedb.org/t/p/w600_and_h900_bestv2${this.backdropPath ?: ""}"
}

/**
 * Returns the URL for the movie or TV show's poster image, if available.
 * The URL follows the format "https://www.themoviedb.org/t/p/w600_and_h900_bestv2" followed
 * by the poster path, or an empty string if the poster path is null or empty.
 *
 * @return The URL for the movie or TV show's poster image.
 */
fun MovieTvEntity.getImagePoster(): String {
    return "https://www.themoviedb.org/t/p/w600_and_h900_bestv2${this.posterPath ?: ""}"
}

// TODO: ADD UNIT TEST
fun MovieTvEntity.formatGenres(): String {
    if (genres.isEmpty()) return "-"
    return genres.joinToString(separator = " \u25CF ")
}
