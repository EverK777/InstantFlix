package com.challenge.instantflix.presentation.ui.composables

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.paging.compose.LazyPagingItems
import com.challenge.instantflix.R
import com.challenge.instantflix.core.data.model.MovieTvEntity
import com.challenge.instantflix.core.data.model.formatGenres
import kotlinx.coroutines.delay

@Composable
fun TrendingPagerComposable(
    trendingMoviesAndTvShows: LazyPagingItems<MovieTvEntity>,
) {
    var alphaValue = 1f
    val animationAlpha = animateFloatAsState(targetValue = alphaValue)
    val items = trendingMoviesAndTvShows.itemSnapshotList.items
    if (items.isNotEmpty()) {
        val currentItemIndex = remember {
            mutableStateOf(0)
        }
        val currentItem = remember {
            mutableStateOf(items[currentItemIndex.value])
        }

        LaunchedEffect(key1 = alphaValue) {
            delay(2000)
            alphaValue = 0f
            val newIndex = currentItemIndex.value + 1
            if (items.size > newIndex) {
                currentItemIndex.value = newIndex
                currentItem.value = items[newIndex]
            } else {
                currentItemIndex.value = 0
                currentItem.value = items[0]
            }
            delay(200)
            alphaValue = 1f
        }

        ConstraintLayout(
            modifier = Modifier.alpha(animationAlpha.value)
                .fillMaxWidth(),
        ) {
            val (background, description) = createRefs()
            Box(modifier = Modifier.wrapContentSize()) {
                PosterImageComposable(posterPath = currentItem.value.posterPath ?: "")
            }
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .constrainAs(description) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = currentItem.value.title ?: "",
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.W800,
                )

                Text(
                    text = currentItem.value.formatGenres(),
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleSmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 20.dp),
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_info_24),
                        contentDescription = "Info icon",
                    )
                    Text(
                        text = stringResource(R.string.info),
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 20.dp),
                    )
                }
            }
        }
    }
}
