package com.challenge.instantflix.core.data.model

import com.theblackbit.instantflix.core.data.model.MovieTvEntity
import com.theblackbit.instantflix.core.data.model.RequestCategory
import com.theblackbit.instantflix.core.data.model.TypeRequest
import com.theblackbit.instantflix.core.data.model.getImageBackDrop
import com.theblackbit.instantflix.core.data.model.getImagePoster
import junit.framework.TestCase.assertEquals
import org.junit.Test

class MovieTvEntityTest {

    @Test
    fun testGetImageBackDrop_withBackdropPath() {
        val movieTv = MovieTvEntity(
            id = 1,
            title = "test",
            overview = "",
            popularity = 0.0,
            voteAverage = 0.0,
            releaseDate = "",
            posterPath = "",
            backdropPath = "/abc123.jpg",
            genres = emptyList(),
            page = 1,
            requestCategory = RequestCategory.TRENDING,
            totalResults = 1,
            typeRequest = TypeRequest.MOVIE,
        )
        val expectedUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/abc123.jpg"
        assertEquals(expectedUrl, movieTv.getImageBackDrop())
    }

    @Test
    fun testGetImageBackDrop_withoutBackdropPath() {
        val movieTv = MovieTvEntity(
            id = 1,
            title = "test",
            overview = "",
            popularity = 0.0,
            voteAverage = 0.0,
            releaseDate = "",
            posterPath = "",
            backdropPath = "",
            genres = emptyList(),
            page = 1,
            requestCategory = RequestCategory.TRENDING,
            totalResults = 1,
            typeRequest = TypeRequest.MOVIE,
        )
        val expectedUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2"
        assertEquals(expectedUrl, movieTv.getImageBackDrop())
    }

    @Test
    fun testGetImagePoster_withPosterPath() {
        val movieTv = MovieTvEntity(
            id = 1,
            title = "test",
            overview = "",
            popularity = 0.0,
            voteAverage = 0.0,
            releaseDate = "",
            posterPath = "/abc123.jpg",
            backdropPath = "",
            genres = emptyList(),
            page = 1,
            requestCategory = RequestCategory.TRENDING,
            totalResults = 1,
            typeRequest = TypeRequest.MOVIE,
        )
        val expectedUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/abc123.jpg"
        assertEquals(expectedUrl, movieTv.getImagePoster())
    }

    @Test
    fun testGetImagePoster_withoutPosterPath() {
        val movieTv = MovieTvEntity(
            id = 1,
            title = "test",
            overview = "",
            popularity = 0.0,
            voteAverage = 0.0,
            releaseDate = "",
            posterPath = "",
            backdropPath = "",
            genres = emptyList(),
            page = 1,
            requestCategory = RequestCategory.TRENDING,
            totalResults = 1,
            typeRequest = TypeRequest.MOVIE,
        )
        val expectedUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2"
        assertEquals(expectedUrl, movieTv.getImagePoster())
    }
}
