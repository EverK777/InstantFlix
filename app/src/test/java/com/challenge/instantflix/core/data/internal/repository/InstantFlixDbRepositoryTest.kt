package com.challenge.instantflix.core.data.internal.repository

import androidx.paging.PagingSource
import com.challenge.instantflix.core.data.internal.datasource.GenreDao
import com.challenge.instantflix.core.data.internal.datasource.MovieTVDao
import com.challenge.instantflix.core.data.model.Genre
import com.challenge.instantflix.core.data.model.MovieTvEntity
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class InstantFlixDbRepositoryTest {

    private lateinit var instantFlixDbRepository: InstantFlixDbRepository
    private lateinit var movieTVDao: MovieTVDao
    private lateinit var genreDao: GenreDao
    private lateinit var mockPagingSource: PagingSource<Int, MovieTvEntity>
    private lateinit var mockMovieTvEntity: MovieTvEntity
    private lateinit var mockGenre: Genre

    @Before
    fun setUp() {
        movieTVDao = mockk()
        genreDao = mockk()
        mockPagingSource = mockk()
        mockMovieTvEntity = mockk()
        mockGenre = mockk()
        instantFlixDbRepository = InstantFlixDbRepository(movieTVDao, genreDao)
    }

    @Test
    fun `whenInsertOrUpdateListOfMovieTvEntity upsertMovieOrTvCached method of MovieTVDao should be called`(): Unit =
        runBlocking {
            val dataToInset = listOf(mockMovieTvEntity)
            coJustRun { movieTVDao.upsertMovieOrTvCached(dataToInset) }

            instantFlixDbRepository.upsertMovieOrTvCached(dataToInset)

            coVerify {
                movieTVDao.upsertMovieOrTvCached(dataToInset)
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
            coJustRun { genreDao.upsertGenre(mockGenre) }

            instantFlixDbRepository.upsertGenre(mockGenre)

            coVerify {
                genreDao.upsertGenre(mockGenre)
            }
        }

    @Test
    fun `whenRequestAGenre should returns the expected one`(): Unit = runBlocking {
        coEvery {
            genreDao.getGenre(1)
        } returns mockGenre

        val result = instantFlixDbRepository.getGenre(1)

        assertEquals(result, mockGenre)
    }
}
