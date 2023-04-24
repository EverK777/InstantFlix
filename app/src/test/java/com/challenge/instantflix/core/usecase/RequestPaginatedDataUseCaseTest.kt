package com.challenge.instantflix.core.usecase

import androidx.paging.Pager
import com.challenge.instantflix.core.data.model.MovieTvEntity
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class RequestPaginatedDataUseCaseTest {
    private val trendingMoviesAndTvShowsPager = mockk<Pager<Int, MovieTvEntity>>()

    private lateinit var requestTrendingMoviesAndTvShowsUseCaseImpl: RequestPaginatedDataUseCaseImpl

    @Before
    fun setUp() {
        requestTrendingMoviesAndTvShowsUseCaseImpl =
            RequestPaginatedDataUseCaseImpl(trendingMoviesAndTvShowsPager)
    }

    @Test
    fun `when getTrendingPager should return the correct one`() {
        val result = requestTrendingMoviesAndTvShowsUseCaseImpl.pager
        assertEquals(trendingMoviesAndTvShowsPager, result)
    }
}
