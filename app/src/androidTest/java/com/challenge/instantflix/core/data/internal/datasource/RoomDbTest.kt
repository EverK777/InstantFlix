package com.challenge.instantflix.core.data.internal.datasource

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.challenge.instantflix.core.data.model.Genre
import com.challenge.instantflix.core.data.model.MovieTvEntity
import com.challenge.instantflix.core.data.model.RequestCategory
import com.challenge.instantflix.core.data.model.TypeRequest
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RoomDbTest {

    private lateinit var movieTVDao: MovieTVDao
    private lateinit var genreDao: GenreDao
    private lateinit var db: InstantFlixDB

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, InstantFlixDB::class.java)
            .addTypeConverter(DBConverters())
            .build()
        movieTVDao = db.movieTBDao()
        genreDao = db.genreDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun when_insertMovieCached_then_get_it_successful(): Unit = runBlocking {
        val movieCached = getFakeMovieCached(
            id = 1,
            requestCategory = RequestCategory.TRENDING,
            typeRequest = TypeRequest.MOVIE,
        )
        movieTVDao.upsertMovieOrTvCached(listOf(movieCached))
        val result = movieTVDao.getMovieTvCached(1)
        assertEquals(result, movieCached)
    }

    @Test
    fun when_getMoviesOrTvShoesByCategoryAndTypeList_then_return_list_of_moviesByCategory(): Unit =
        runBlocking {
            insertTrendingFakeMoviesAndSeries(RequestCategory.TRENDING)

            val data = movieTVDao.getMoviesOrTvShoesByCategoryAndTypeList(
                requestCategory = RequestCategory.TRENDING.valueName,
                typeRequest = TypeRequest.MOVIE.type,
                1,
            )

            assertTrue(
                data.all {
                    it.requestCategory == RequestCategory.TRENDING &&
                        it.typeRequest == TypeRequest.MOVIE
                },
            )
        }

    @Test
    fun when_getTvShowsByCategory_then_return_list_of_tvShowsByCategory(): Unit =
        runBlocking {
            insertTrendingFakeMoviesAndSeries(
                requestCategory = RequestCategory.TRENDING,
                genreIdRequested = 1,
            )

            val data = movieTVDao.getMoviesOrTvShoesByCategoryAndTypeList(
                requestCategory = RequestCategory.TRENDING.valueName,
                typeRequest = TypeRequest.TV_SHOW.type,
                1,
            )

            assertTrue(
                data.all {
                    it.requestCategory == RequestCategory.TRENDING &&
                        it.typeRequest == TypeRequest.TV_SHOW
                },
            )
        }

    @Test
    fun when_insertAGenre_then_get_it_successful(): Unit = runBlocking {
        val genre = Genre(
            id = 1,
            name = "test",
        )
        genreDao.upsertGenre(genre)
        val result = genreDao.getGenre(1)
        assertEquals(result, genre)
    }

    @Test
    fun testClearAll(): Unit = runBlocking {
        insertTrendingFakeMoviesAndSeries(
            requestCategory = RequestCategory.TRENDING,
        )

        movieTVDao.clearByCategoryAndType(
            requestCategory = RequestCategory.TRENDING.valueName,
            typeRequest = TypeRequest.TV_SHOW.type,
        )

        val data = movieTVDao.getMoviesOrTvShoesByCategoryAndTypeList(
            requestCategory = RequestCategory.TRENDING.valueName,
            typeRequest = TypeRequest.TV_SHOW.type,
            pageNumber = 1,
        )

        assertTrue(data.isEmpty())
    }

    private suspend fun insertTrendingFakeMoviesAndSeries(
        requestCategory: RequestCategory,
        genreIdRequested: Int? = null,
    ) {
        val moviesAndTvShows = mutableListOf<MovieTvEntity>()

        for (i in 1 until 10) {
            moviesAndTvShows.add(
                getFakeMovieCached(
                    id = i,
                    genreIdRequested = genreIdRequested,
                    requestCategory = requestCategory,
                    typeRequest = if (i % 2 == 0) TypeRequest.MOVIE else TypeRequest.TV_SHOW,
                ),
            )
        }
        insertFakeMoviesAndSeries(moviesAndTvShows)
    }

    private suspend fun insertFakeMoviesAndSeries(list: List<MovieTvEntity>) {
        movieTVDao.upsertMovieOrTvCached(list)
    }
}

private fun getFakeMovieCached(
    id: Int,
    genreIdRequested: Int? = null,
    requestCategory: RequestCategory,
    typeRequest: TypeRequest,
): MovieTvEntity {
    return MovieTvEntity(
        id = id,
        title = "TestTitle",
        overview = "Test overview",
        popularity = 1.0,
        genres = listOf("test", "test", "test"),
        voteAverage = 4.0,
        releaseDate = "10/12/2022",
        posterPath = "test",
        backdropPath = "test",
        genreIdRequested = genreIdRequested,
        requestCategory = requestCategory,
        typeRequest = typeRequest,
        page = 1,
        totalResults = 3,
    )
}
