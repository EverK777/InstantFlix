package com.challenge.instantflix.presentation.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.platform.LocalConfiguration
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
import com.challenge.instantflix.core.data.model.getImagePoster

@Composable
fun TrendingComposable(
    trendingMoviesAndTvShows: LazyPagingItems<MovieTvEntity>,
) {
    val items = trendingMoviesAndTvShows.itemSnapshotList.items
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp.value * 0.75
    val item = remember {
        mutableStateOf<MovieTvEntity?>(null)
    }

    LaunchedEffect(items) {
        if (items.isNotEmpty()) {
            item.value = items.maxBy { it.popularity ?: 0.0 }
        }
    }

    if (item.value != null) {
        ConstraintLayout(
            modifier = Modifier.fillMaxWidth().height(screenHeight.dp),
        ) {
            val (description, poster) = createRefs()
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .constrainAs(poster) {
                        top.linkTo(parent.top)
                    }
                    .fillMaxWidth()
                    .height(screenHeight.dp),
            ) {
                PosterImageComposable(
                    posterPath = item.value!!.getImagePoster(),
                    Modifier.fillMaxSize(),
                )
                BottomGradientComposable(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(screenHeight.dp),
                )
            }
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
                    text = item.value!!.title ?: "",
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.W800,
                )

                Text(
                    text = item.value!!.formatGenres(),
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.labelSmall,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.padding(vertical = 20.dp),
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
                    )
                }
            }
        }
    }
}
