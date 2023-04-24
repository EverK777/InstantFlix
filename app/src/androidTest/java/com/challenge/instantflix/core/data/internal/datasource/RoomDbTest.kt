package com.challenge.instantflix.core.data.internal.datasource

import android.content.Context
import androidx.paging.PagingSource
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
    private lateinit var db: InstantFlixDB

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, InstantFlixDB::class.java)
            .addTypeConverter(DBConverters())
            .build()
        movieTVDao = db.movieTBDao()
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
        movieTVDao.upsertMovieOrTvCached(movieCached)
        val result = movieTVDao.getMovieTvCached(1)
        assertEquals(result, movieCached)
    }

    @Test
    fun when_getMoviesOrTvShowsByCategory_then_return_list_of_moviesAndTvShows(): Unit =
        runBlocking {
            insertTrendingFakeMoviesAndSeries(RequestCategory.TRENDING)

            val pagingSource =
                movieTVDao.getMoviesAndTvShowsByCategory(RequestCategory.TRENDING.valueName)

            val loadResult = pagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 1,
                    loadSize = 20,
                    placeholdersEnabled = false,
                ),
            )

            val loadResultData = (loadResult as? PagingSource.LoadResult.Page)!!.data

            assertTrue(loadResultData.isNotEmpty())

            assertTrue(
                loadResultData.all { it.requestCategory == RequestCategory.TRENDING },
            )

            val numberOfMovies = loadResultData.count { it.typeRequest == TypeRequest.MOVIE }
            val numberOfTvShows = loadResultData.count { it.typeRequest == TypeRequest.TV_SHOW }

            assertEquals(numberOfTvShows, 4)
            assertEquals(numberOfMovies, 4)
        }

    @Test
    fun when_getMoviesByCategory_then_return_list_of_moviesAndTvShows(): Unit =
        runBlocking {
            insertTrendingFakeMoviesAndSeries(RequestCategory.TRENDING)

            val pagingSource =
                movieTVDao.getMoviesOrTvShoesByCategoryAndType(
                    requestCategory = RequestCategory.TRENDING.valueName,
                    typeRequest = TypeRequest.MOVIE.type,
                )

            val loadResult = pagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 1,
                    loadSize = 10,
                    placeholdersEnabled = false,
                ),
            )

            val loadResultData = (loadResult as? PagingSource.LoadResult.Page)!!.data

            assertTrue(
                loadResultData.all {
                    it.requestCategory == RequestCategory.TRENDING &&
                        it.typeRequest == TypeRequest.MOVIE
                },
            )
        }

    @Test
    fun when_getTvShowsByCategory_then_return_list_of_moviesAndTvShows(): Unit =
        runBlocking {
            insertTrendingFakeMoviesAndSeries(
                requestCategory = RequestCategory.TRENDING,
                genreIdRequested = 1,
            )

            val pagingSource =
                movieTVDao.getMoviesOrTvShoesByCategoryAndType(
                    requestCategory = RequestCategory.TRENDING.valueName,
                    typeRequest = TypeRequest.TV_SHOW.type,
                )

            val loadResult = pagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 1,
                    loadSize = 10,
                    placeholdersEnabled = false,
                ),
            )

            val loadResultData = (loadResult as? PagingSource.LoadResult.Page)!!.data

            assertTrue(
                loadResultData.all {
                    it.requestCategory == RequestCategory.TRENDING &&
                        it.typeRequest == TypeRequest.TV_SHOW
                },
            )
        }

    @Test
    fun when_getMoviesAndTvShowsByGenre_then_return_list_of_moviesAndTvShowsByGenre(): Unit =
        runBlocking {
            insertTrendingFakeMoviesAndSeries(
                requestCategory = RequestCategory.TRENDING,
                genreIdRequested = 1,
            )

            val pagingSource =
                movieTVDao.getMoviesAndTvShowsByGenre(
                    genreId = 1,
                )

            val loadResult = pagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 1,
                    loadSize = 10,
                    placeholdersEnabled = false,
                ),
            )

            val loadResultData = (loadResult as? PagingSource.LoadResult.Page)!!.data

            assertTrue(
                loadResultData.all {
                    it.genreIdRequested == 1
                },
            )
        }

    @Test
    fun when_getMoviesByGenre_then_return_list_of_moviesAndTvShowsByGenre(): Unit =
        runBlocking {
            insertTrendingFakeMoviesAndSeries(
                requestCategory = RequestCategory.TRENDING,
                genreIdRequested = 1,
            )

            val pagingSource =
                movieTVDao.getMoviesOrTvShowsByGenreAndType(
                    genreId = 1,
                    typeRequest = TypeRequest.MOVIE.type,
                )

            val loadResult = pagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 1,
                    loadSize = 10,
                    placeholdersEnabled = false,
                ),
            )

            val loadResultData = (loadResult as? PagingSource.LoadResult.Page)!!.data

            assertTrue(
                loadResultData.all {
                    it.genreIdRequested == 1 && it.typeRequest == TypeRequest.MOVIE
                },
            )
        }

    @Test
    fun when_getTvShowsByGenre_then_return_list_of_moviesAndTvShowsByGenre(): Unit =
        runBlocking {
            insertTrendingFakeMoviesAndSeries(
                requestCategory = RequestCategory.TRENDING,
                genreIdRequested = 1,
            )

            val pagingSource =
                movieTVDao.getMoviesOrTvShowsByGenreAndType(
                    genreId = 1,
                    typeRequest = TypeRequest.TV_SHOW.type,
                )

            val loadResult = pagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 1,
                    loadSize = 10,
                    placeholdersEnabled = false,
                ),
            )

            val loadResultData = (loadResult as? PagingSource.LoadResult.Page)!!.data

            assertTrue(
                loadResultData.all {
                    it.genreIdRequested == 1 && it.typeRequest == TypeRequest.TV_SHOW
                },
            )
        }

    @Test
    fun when_insertAGenre_then_get_it_successful(): Unit = runBlocking {
        val genre = Genre(
            id = 1,
            name = "test",
        )
        movieTVDao.upsertGenre(genre)
        val result = movieTVDao.getGenre(1)
        assertEquals(result, genre)
    }

    @Test
    fun testClearAll(): Unit = runBlocking {
        insertTrendingFakeMoviesAndSeries(
            requestCategory = RequestCategory.TRENDING,
        )

        movieTVDao.clearAll()

        val pagingSource =
            movieTVDao.getMoviesAndTvShowsByCategory(RequestCategory.TRENDING.valueName)

        val loadResult = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 1,
                loadSize = 20,
                placeholdersEnabled = false,
            ),
        )
        val loadResultData = (loadResult as? PagingSource.LoadResult.Page)!!.data
        assertTrue(loadResultData.isEmpty())
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
        list.forEach {
            movieTVDao.upsertMovieOrTvCached(it)
        }
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
