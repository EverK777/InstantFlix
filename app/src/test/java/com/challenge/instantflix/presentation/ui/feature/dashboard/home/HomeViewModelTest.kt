package com.challenge.instantflix.presentation.ui.feature.dashboard.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.Pager
import com.challenge.instantflix.core.data.model.MovieTvEntity
import com.challenge.instantflix.core.usecase.RequestTrendingMoviesAndTvShowsUseCase
import com.challenge.instantflix.presentation.feature.dashboard.home.HomeViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: HomeViewModel

    private lateinit var mockUseCase: RequestTrendingMoviesAndTvShowsUseCase

    private lateinit var movieTvEntity: MovieTvEntity

    private val mockPager: Pager<Int, MovieTvEntity> = mockk()

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        movieTvEntity = mockk()
        mockUseCase = mockk(relaxed = true)
        viewModel = HomeViewModel(mockUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `trendingMoviesTvShowsPagerFlow returns correct data`() = runTest(testDispatcher) {
        every { mockUseCase.trendingMoviesAndTvShowsPager }.returns(mockPager)

        val job = launch {
            viewModel.trendingMoviesTvShowsPagerFlow.single()
        }
        advanceUntilIdle()
        verify { mockUseCase.trendingMoviesAndTvShowsPager }
        job.cancel()
    }
}
