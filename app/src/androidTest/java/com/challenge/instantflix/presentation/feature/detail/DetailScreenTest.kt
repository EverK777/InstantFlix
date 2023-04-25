package com.challenge.instantflix.presentation.feature.detail

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.challenge.instantflix.core.data.model.formatGenres
import com.challenge.instantflix.core.data.model.getImagePoster
import com.challenge.instantflix.helpers.fakeTrendingMovie
import com.challenge.instantflix.presentation.ui.theme.InstantflixTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val fakeData = fakeTrendingMovie

    @Before
    fun setUp() {
        composeTestRule.setContent {
            InstantflixTheme(true) {
                Surface(color = MaterialTheme.colorScheme.background) {
                    DetailView(
                        posterImage = fakeData.getImagePoster(),
                        typeRequest = fakeData.typeRequest.type,
                        name = fakeData.title!!,
                        genres = fakeData.formatGenres(),
                        overview = fakeData.overview!!,
                        voteAverage = fakeData.voteAverage.toString(),
                        releaseYear = fakeData.releaseDate!!,
                    ) {
                    }
                }
            }
        }
    }

    @Test
    fun testDataOfDetailScreen() {
        composeTestRule.mainClock.advanceTimeBy(1000)
        composeTestRule.onNodeWithText(fakeData.typeRequest.type.uppercase()).assertExists()
        composeTestRule.onNodeWithText(fakeData.title!!).assertExists()
        composeTestRule.onNodeWithText(fakeData.formatGenres()).assertExists()
        composeTestRule.onNodeWithText(fakeData.overview!!).assertExists()
        composeTestRule.onNodeWithText(fakeData.voteAverage.toString()).assertExists()
        composeTestRule.onNodeWithText(fakeData.releaseDate!!).assertExists()
    }
}
