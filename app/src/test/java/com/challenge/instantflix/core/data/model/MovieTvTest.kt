package com.challenge.instantflix.core.data.model

import junit.framework.TestCase.assertEquals
import org.junit.Test

class MovieTvTest {

    @Test
    fun testGetImageBackDrop_withBackdropPath() {
        val movieTv = MovieTv(
            id = 1,
            title = "test",
            overview = "",
            popularity = 0.0,
            voteAverage = 0.0,
            releaseDate = "",
            posterPath = "",
            backdropPath = "/abc123.jpg",
            genreIds = emptyList(),
        )
        val expectedUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/abc123.jpg"
        assertEquals(expectedUrl, movieTv.getImageBackDrop())
    }

    @Test
    fun testGetImageBackDrop_withoutBackdropPath() {
        val movieTv = MovieTv(
            id = 1,
            title = "test",
            overview = "",
            popularity = 0.0,
            voteAverage = 0.0,
            releaseDate = "",
            posterPath = "",
            backdropPath = "",
            genreIds = emptyList(),
        )
        val expectedUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2"
        assertEquals(expectedUrl, movieTv.getImageBackDrop())
    }

    @Test
    fun testGetImagePoster_withPosterPath() {
        val movieTv = MovieTv(
            id = 1,
            title = "test",
            overview = "",
            popularity = 0.0,
            voteAverage = 0.0,
            releaseDate = "",
            posterPath = "/abc123.jpg",
            backdropPath = "",
            genreIds = emptyList(),
        )
        val expectedUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/abc123.jpg"
        assertEquals(expectedUrl, movieTv.getImagePoster())
    }

    @Test
    fun testGetImagePoster_withoutPosterPath() {
        val movieTv = MovieTv(
            id = 1,
            title = "test",
            overview = "",
            popularity = 0.0,
            voteAverage = 0.0,
            releaseDate = "",
            posterPath = "",
            backdropPath = "",
            genreIds = emptyList(),
        )
        val expectedUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2"
        assertEquals(expectedUrl, movieTv.getImagePoster())
    }
}
