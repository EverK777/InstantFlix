package com.challenge.instantflix.core.data.model

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.Test

class MovieTvTest {
    @Test
    fun `toMovieTvEntity should return a valid MovieTvEntity object`() {
        val movieTv = MovieTv(
            id = 123,
            title = "The Matrix",
            overview = "A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.",
            popularity = 8.4,
            genreIds = listOf(28, 878),
            voteAverage = 8.1,
            releaseDate = "1999-03-30",
            posterPath = "/hEpWvX6Bp79eLxY1kX5ZZJcme5U.jpg",
            backdropPath = "/fNG7i7RqMErkcqhohV2a6cV1Ehy.jpg",
        )
        val genreIdRequest = 28
        val requestCategory = RequestCategory.BY_GENRE
        val typeRequest = TypeRequest.MOVIE
        val page = 1
        val totalResult = 100

        val result = movieTv.toMovieTvEntity(
            genreIdRequest = genreIdRequest,
            requestCategory = requestCategory,
            typeRequest = typeRequest,
            page = page,
            totalResult = totalResult,
        )

        assertNotNull(result)
        assertEquals(movieTv.id, result.id)
        assertEquals(movieTv.title, result.title)
        assertEquals(movieTv.overview, result.overview)
        assertEquals(movieTv.popularity, result.popularity)
        assertEquals(movieTv.genreIds, result.genreIds)
        assertEquals(movieTv.voteAverage, result.voteAverage)
        assertEquals(movieTv.releaseDate, result.releaseDate)
        assertEquals(movieTv.posterPath, result.posterPath)
        assertEquals(movieTv.backdropPath, result.backdropPath)
        assertEquals(genreIdRequest, result.genreIdRequested)
        assertEquals(requestCategory, result.requestCategory)
        assertEquals(typeRequest, result.typeRequest)
        assertEquals(page, result.page)
        assertEquals(totalResult, result.totalResults)
    }
}
