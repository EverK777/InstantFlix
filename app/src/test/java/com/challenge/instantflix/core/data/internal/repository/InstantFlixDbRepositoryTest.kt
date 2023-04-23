package com.challenge.instantflix.core.data.internal.repository

import androidx.paging.PagingSource
import com.challenge.instantflix.core.data.internal.datasource.MovieTVDao
import com.challenge.instantflix.core.data.model.Genre
import com.challenge.instantflix.core.data.model.MovieTvEntity
import com.challenge.instantflix.core.data.model.RequestCategory
import com.challenge.instantflix.core.data.model.TypeRequest
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class InstantFlixDbRepositoryTest {

    private lateinit var instantFlixDbRepository: InstantFlixDbRepository
    private lateinit var movieTVDao: MovieTVDao
    private lateinit var mockPagingSource: PagingSource<Int, MovieTvEntity>
    private lateinit var mockMovieTvEntity: MovieTvEntity
    private lateinit var mockGenre: Genre

    @Before
    fun setUp() {
        movieTVDao = mockk()
        mockPagingSource = mockk()
        mockMovieTvEntity = mockk()
        mockGenre = mockk()
        instantFlixDbRepository = InstantFlixDbRepository(movieTVDao)
    }

    @Test
    fun `whenRequestTopRatedMoviesAndTvShows should return the expected paging source`() {
        every {
            movieTVDao.getMoviesAndTvShowsByCategory(RequestCategory.TOP_RATED.valueName)
        } returns mockPagingSource

        val result =
            instantFlixDbRepository.getMoviesAndTvShowsByCategory(RequestCategory.TOP_RATED)

        assertEquals(result, mockPagingSource)
    }

    @Test
    fun `whenRequestByGenreMoviesAndTvShows should return the expected paging source`() {
        every {
            movieTVDao.getMoviesAndTvShowsByCategory(RequestCategory.BY_GENRE.valueName)
        } returns mockPagingSource

        val result = instantFlixDbRepository.getMoviesAndTvShowsByCategory(RequestCategory.BY_GENRE)

        assertEquals(result, mockPagingSource)
    }

    @Test
    fun `whenRequestPopularMoviesAndTvShows should return the expected paging source`() {
        every {
            movieTVDao.getMoviesAndTvShowsByCategory(RequestCategory.POPULAR.valueName)
        } returns mockPagingSource

        val result = instantFlixDbRepository.getMoviesAndTvShowsByCategory(RequestCategory.POPULAR)

        assertEquals(result, mockPagingSource)
    }

    @Test
    fun `whenRequestTrendingMoviesAndTvShows should return the expected paging source`() {
        every {
            movieTVDao.getMoviesAndTvShowsByCategory(RequestCategory.TRENDING.valueName)
        } returns mockPagingSource

        val result = instantFlixDbRepository.getMoviesAndTvShowsByCategory(RequestCategory.TRENDING)

        assertEquals(result, mockPagingSource)
    }

    @Test
    fun `whenRequestTopRatedMovies should return the expected paging source `() {
        every {
            movieTVDao.getMoviesOrTvShoesByCategoryAndType(
                requestCategory = RequestCategory.TOP_RATED.valueName,
                typeRequest = TypeRequest.MOVIE.type,
            )
        } returns mockPagingSource

        val result = instantFlixDbRepository.getMoviesOrTvShoesByCategoryAndType(
            requestCategory = RequestCategory.TOP_RATED,
            typeRequest = TypeRequest.MOVIE,
        )

        assertEquals(result, mockPagingSource)
    }

    @Test
    fun `whenRequestTopRatedTvShow should return the expected paging source `() {
        every {
            movieTVDao.getMoviesOrTvShoesByCategoryAndType(
                requestCategory = RequestCategory.TOP_RATED.valueName,
                typeRequest = TypeRequest.TV_SHOW.type,
            )
        } returns mockPagingSource

        val result = instantFlixDbRepository.getMoviesOrTvShoesByCategoryAndType(
            requestCategory = RequestCategory.TOP_RATED,
            typeRequest = TypeRequest.TV_SHOW,
        )

        assertEquals(result, mockPagingSource)
    }

    @Test
    fun `whenRequestByGenreMovies should return the expected paging source `() {
        every {
            movieTVDao.getMoviesOrTvShoesByCategoryAndType(
                requestCategory = RequestCategory.BY_GENRE.valueName,
                typeRequest = TypeRequest.MOVIE.type,
            )
        } returns mockPagingSource

        val result = instantFlixDbRepository.getMoviesOrTvShoesByCategoryAndType(
            requestCategory = RequestCategory.BY_GENRE,
            typeRequest = TypeRequest.MOVIE,
        )

        assertEquals(result, mockPagingSource)
    }

    @Test
    fun `whenRequestByGenreTvShow should return the expected paging source `() {
        every {
            movieTVDao.getMoviesOrTvShoesByCategoryAndType(
                requestCategory = RequestCategory.BY_GENRE.valueName,
                typeRequest = TypeRequest.TV_SHOW.type,
            )
        } returns mockPagingSource

        val result = instantFlixDbRepository.getMoviesOrTvShoesByCategoryAndType(
            requestCategory = RequestCategory.BY_GENRE,
            typeRequest = TypeRequest.TV_SHOW,
        )

        assertEquals(result, mockPagingSource)
    }

    @Test
    fun `whenRequestPopularMovies should return the expected paging source `() {
        every {
            movieTVDao.getMoviesOrTvShoesByCategoryAndType(
                requestCategory = RequestCategory.POPULAR.valueName,
                typeRequest = TypeRequest.MOVIE.type,
            )
        } returns mockPagingSource

        val result = instantFlixDbRepository.getMoviesOrTvShoesByCategoryAndType(
            requestCategory = RequestCategory.POPULAR,
            typeRequest = TypeRequest.MOVIE,
        )

        assertEquals(result, mockPagingSource)
    }

    @Test
    fun `whenRequestPopularTvShows should return the expected paging source `() {
        every {
            movieTVDao.getMoviesOrTvShoesByCategoryAndType(
                requestCategory = RequestCategory.POPULAR.valueName,
                typeRequest = TypeRequest.TV_SHOW.type,
            )
        } returns mockPagingSource

        val result = instantFlixDbRepository.getMoviesOrTvShoesByCategoryAndType(
            requestCategory = RequestCategory.POPULAR,
            typeRequest = TypeRequest.TV_SHOW,
        )

        assertEquals(result, mockPagingSource)
    }

    @Test
    fun `whenRequestTrendingMovies should return the expected paging source `() {
        every {
            movieTVDao.getMoviesOrTvShoesByCategoryAndType(
                requestCategory = RequestCategory.TRENDING.valueName,
                typeRequest = TypeRequest.MOVIE.type,
            )
        } returns mockPagingSource

        val result = instantFlixDbRepository.getMoviesOrTvShoesByCategoryAndType(
            requestCategory = RequestCategory.TRENDING,
            typeRequest = TypeRequest.MOVIE,
        )

        assertEquals(result, mockPagingSource)
    }

    @Test
    fun `whenRequestTrendingTvShows should return the expected paging source `() {
        every {
            movieTVDao.getMoviesOrTvShoesByCategoryAndType(
                requestCategory = RequestCategory.TRENDING.valueName,
                typeRequest = TypeRequest.TV_SHOW.type,
            )
        } returns mockPagingSource

        val result = instantFlixDbRepository.getMoviesOrTvShoesByCategoryAndType(
            requestCategory = RequestCategory.TRENDING,
            typeRequest = TypeRequest.TV_SHOW,
        )

        assertEquals(result, mockPagingSource)
    }

    @Test
    fun `whenInsertOrUpdateAMovieTvShow upsertMovieOrTvCached method of MovieTVDao should be called`(): Unit =
        runBlocking {
            coJustRun { movieTVDao.upsertMovieOrTvCached(mockMovieTvEntity) }

            instantFlixDbRepository.upsertMovieOrTvCached(mockMovieTvEntity)

            coVerify {
                movieTVDao.upsertMovieOrTvCached(mockMovieTvEntity)
            }
        }

    @Test
    fun `whenRequestAMovieTvEntity should returns the expected one`(): Unit = runBlocking {
        coEvery {
            movieTVDao.getMovieTvCached(1)
        } returns mockMovieTvEntity

        val result = instantFlixDbRepository.getMovieTvCached(1)

        assertEquals(result, mockMovieTvEntity)
    }

    @Test
    fun `whenInsertOrUpdateAGenre upsertGenre method of MovieTVDao should be called`(): Unit =
        runBlocking {
            coJustRun { movieTVDao.upsertGenre(mockGenre) }

            instantFlixDbRepository.upsertGenre(mockGenre)

            coVerify {
                movieTVDao.upsertGenre(mockGenre)
            }
        }

    @Test
    fun `whenRequestAGenre should returns the expected one`(): Unit = runBlocking {
        coEvery {
            movieTVDao.getGenre(1)
        } returns mockGenre

        val result = instantFlixDbRepository.getGenre(1)

        assertEquals(result, mockGenre)
    }

    @Test
    fun `whenClearAllCache clearAll method of MovieTVDao should be called`(): Unit =
        runBlocking {
            coJustRun { movieTVDao.clearAll() }

            instantFlixDbRepository.clearAll()

            coVerify {
                movieTVDao.clearAll()
            }
        }
}
