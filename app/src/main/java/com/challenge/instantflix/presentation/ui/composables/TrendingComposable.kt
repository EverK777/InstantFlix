package com.challenge.instantflix.presentation.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.challenge.instantflix.R
import com.challenge.instantflix.core.data.model.MovieTvEntity
import com.challenge.instantflix.core.data.model.formatGenres
import com.challenge.instantflix.core.data.model.getImagePoster
import com.challenge.instantflix.core.utils.TestTags.IMAGE_POSTER_TAG
import com.challenge.instantflix.core.utils.TestTags.INFO_ICON_TEXT_TAG
import com.challenge.instantflix.core.utils.TestTags.POSTER_GENRES_TAG

@Composable
fun TrendingComposable(
    item: () -> MovieTvEntity?,
    onItemClick: () -> Unit,
) {
    val configuration = LocalConfiguration.current
    val heightPoster = configuration.screenHeightDp.dp.value * 0.75

    val interactionSource = remember { MutableInteractionSource() }

    if (item.invoke() != null) {
        ConstraintLayout(
            modifier = Modifier.fillMaxWidth().height(heightPoster.dp),
        ) {
            val (description, poster) = createRefs()
            PosterGradientComposable(
                modifier = Modifier
                    .constrainAs(poster) {
                        top.linkTo(parent.top)
                    },
                imagePoster = { item.invoke()?.getImagePoster() ?: "" },
                screenHeight = { heightPoster },
            )
            Column(
                modifier = Modifier
                    .padding(bottom = 200.dp, start = 50.dp, end = 50.dp)
                    .constrainAs(description) {
                        top.linkTo(poster.bottom)
                        bottom.linkTo(poster.bottom)
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = item.invoke()?.title ?: "",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.W800,
                    modifier = Modifier.testTag(IMAGE_POSTER_TAG),
                )

                Text(
                    text = item.invoke()?.formatGenres() ?: "",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.labelSmall,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.padding(vertical = 20.dp).testTag(POSTER_GENRES_TAG),
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.clickable(
                        interactionSource = interactionSource,
                        indication = null,
                    ) {
                        onItemClick.invoke()
                    },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_info_24),
                        contentDescription = "Info icon",
                    )
                    Text(
                        text = stringResource(R.string.info),
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.testTag(INFO_ICON_TEXT_TAG),
                    )
                }
            }
        }
    }
}
