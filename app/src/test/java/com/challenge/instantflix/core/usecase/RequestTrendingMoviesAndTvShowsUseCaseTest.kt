package com.challenge.instantflix.core.usecase

import androidx.paging.Pager
import com.challenge.instantflix.core.data.model.MovieTvEntity
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class RequestTrendingMoviesAndTvShowsUseCaseTest {
    private val trendingMoviesAndTvShowsPager = mockk<Pager<Int, MovieTvEntity>>()

    private lateinit var requestTrendingMoviesAndTvShowsUseCaseImpl: RequestTrendingMoviesAndTvShowsUseCaseImpl

    @Before
    fun setUp() {
        requestTrendingMoviesAndTvShowsUseCaseImpl =
            RequestTrendingMoviesAndTvShowsUseCaseImpl(trendingMoviesAndTvShowsPager)
    }

    @Test
    fun `when getTrendingPager should return the correct one`() {
        val result = requestTrendingMoviesAndTvShowsUseCaseImpl.trendingMoviesAndTvShowsPager
        assertEquals(trendingMoviesAndTvShowsPager, result)
    }
}
