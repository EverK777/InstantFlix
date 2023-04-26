package com.challenge.instantflix.presentation.feature.dashboard.home.tab

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollToIndex
import androidx.paging.PagingData
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.challenge.instantflix.core.data.model.MovieTvEntity
import com.challenge.instantflix.core.data.model.MovieTvResponse
import com.challenge.instantflix.core.data.model.RequestCategory
import com.challenge.instantflix.core.data.model.TypeRequest
import com.challenge.instantflix.core.data.model.formatGenres
import com.challenge.instantflix.core.data.model.toMovieTvEntity
import com.challenge.instantflix.core.utils.JsonFileReader
import com.challenge.instantflix.core.utils.TestTags.IMAGE_POSTER_TAG
import com.challenge.instantflix.core.utils.TestTags.MOVIES_CONTAINER_TAG
import com.challenge.instantflix.core.utils.TestTags.POSTER_GENRES_TAG
import com.challenge.instantflix.core.utils.fromJson
import com.challenge.instantflix.helpers.fakeTrendingMovie
import com.challenge.instantflix.presentation.feature.dashboard.home.HomeScreen
import com.challenge.instantflix.presentation.ui.theme.InstantflixTheme
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    private lateinit var jsonFileReader: JsonFileReader

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().context
        jsonFileReader = JsonFileReader(context)
    }

    @Test
    fun testHomeScreenComposableWithData() {
        val fakePopularMovies = getFakeData(
            fileName = "popular_movies_response.json",
            requestCategory = RequestCategory.POPULAR,
            TypeRequest.MOVIE,
        )
        val fakePopularTvShows = getFakeData(
            fileName = "popular_tv_shows_response.json",
            requestCategory = RequestCategory.POPULAR,
            TypeRequest.TV_SHOW,
        )
        val fakeTopRatedMovies = getFakeData(
            fileName = "top_rated_movies_response.json",
            requestCategory = RequestCategory.TOP_RATED,
            TypeRequest.MOVIE,
        )
        val fakeTopRatedTvShows = getFakeData(
            fileName = "top_rated_tv_shows_response.json",
            requestCategory = RequestCategory.TOP_RATED,
            TypeRequest.TV_SHOW,
        )

        val pagingDataPopularMovies = PagingData.from(fakePopularMovies)
        val pagingDataPopularTvShows = PagingData.from(fakePopularTvShows)
        val pagingDataTopRatedMovies = PagingData.from(fakeTopRatedMovies)
        val pagingDataTopRatedTvShows = PagingData.from(fakeTopRatedTvShows)

        composeTestRule.setContent {
            InstantflixTheme(true) {
                Surface(color = MaterialTheme.colorScheme.background) {
                    HomeScreen(
                        movieTvEntity = MutableStateFlow(fakeTrendingMovie),
                        popularMovies = flowOf(pagingDataPopularMovies),
                        popularTvShows = flowOf(pagingDataPopularTvShows),
                        topRatedMovies = flowOf(pagingDataTopRatedMovies),
                        topRatedTvShows = flowOf(pagingDataTopRatedTvShows),
                        onItemClick = {},
                    )
                }
            }
        }

        composeTestRule.onNodeWithTag(IMAGE_POSTER_TAG)
            .assertTextEquals(fakeTrendingMovie.title!!)
        composeTestRule.onNodeWithTag(POSTER_GENRES_TAG)
            .assertTextEquals(fakeTrendingMovie.formatGenres())
        composeTestRule.onNodeWithText("Info").assertExists()

        composeTestRule.onNodeWithTag(MOVIES_CONTAINER_TAG).performScrollToIndex(1)
        composeTestRule.onNodeWithTag("Popular movies")
            .performScrollToIndex(fakePopularMovies.lastIndex)

        composeTestRule.onNodeWithTag(MOVIES_CONTAINER_TAG).performScrollToIndex(2)
        composeTestRule.onNodeWithTag("Popular tv shows")
            .performScrollToIndex(fakePopularTvShows.lastIndex)

        composeTestRule.onNodeWithTag(MOVIES_CONTAINER_TAG).performScrollToIndex(3)
        composeTestRule.onNodeWithTag("Top rated movies")
            .performScrollToIndex(fakeTopRatedMovies.lastIndex)

        composeTestRule.onNodeWithTag(MOVIES_CONTAINER_TAG).performScrollToIndex(4)
        composeTestRule.onNodeWithTag("Top rated tv shows")
            .performScrollToIndex(fakeTopRatedTvShows.lastIndex)
    }

    private fun getFakeData(
        fileName: String,
        requestCategory: RequestCategory,
        typeRequest: TypeRequest,
    ): List<MovieTvEntity> {
        val responseJson = jsonFileReader.getJsonFromAsset(fileName)
        val gson = Gson()
        val response = gson.fromJson<MovieTvResponse>(responseJson)
        return response.result.map {
            it.toMovieTvEntity(
                requestCategory = requestCategory,
                typeRequest = typeRequest,
                page = response.page,
                totalResult = response.totalResults,
                genres = listOf("Comedy", " Fantasy", "Action", "Drama"),
            )
        }
    }
}
