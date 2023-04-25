package com.challenge.instantflix.presentation.feature.detail

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.challenge.instantflix.R
import com.challenge.instantflix.presentation.ui.composables.PosterGradientComposable
import com.challenge.instantflix.presentation.ui.composables.TopGradientComposable
import kotlinx.coroutines.delay

@Composable
fun DetailView(
    posterImage: String,
    typeRequest: String,
    name: String,
    genres: String,
    overview: String,
    voteAverage: String,
    releaseYear: String,
    onBackPress: () -> Unit,
) {
    val configuration = LocalConfiguration.current
    val heightPoster = configuration.screenHeightDp.dp.value * 0.65

    val shouldShowInfo = remember {
        mutableStateOf(false)
    }

    val shouldShowShip1 = remember {
        mutableStateOf(false)
    }

    val shouldShowShip2 = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(shouldShowInfo) {
        delay(300)
        shouldShowInfo.value = true
        delay(100)
        shouldShowShip1.value = true
        delay(100)
        shouldShowShip2.value = true
    }

    val typeRequestText =
        if (typeRequest == "tv") {
            stringResource(R.string.tv_shows)
        } else {
            stringResource(
                R.string.movie,
            )
        }

    ConstraintLayout(
        modifier = Modifier.fillMaxSize(),
    ) {
        val (topGradient, poster, content) = createRefs()

        PosterGradientComposable(
            modifier = Modifier
                .constrainAs(poster) {
                    top.linkTo(parent.top)
                },
            imagePoster = { posterImage },
            screenHeight = { heightPoster },
        )

        Column(
            modifier = Modifier.constrainAs(
                content,
            ) {
                top.linkTo(poster.bottom)
                bottom.linkTo(parent.bottom)
            }.offset { IntOffset(x = 0, y = -150.dp.toPx().toInt()) },
        ) {
            AnimatedVisibility(
                visible = shouldShowInfo.value,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically(),
            ) {
                Column {
                    Text(
                        text = typeRequestText,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.W800,
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.padding(start = 16.dp),
                    )

                    Text(
                        text = name,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.W800,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(start = 16.dp),
                    )

                    Text(
                        text = genres,
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.labelMedium,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Light,
                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 16.dp),
                    )
                }
            }

            Row(
                modifier = Modifier.padding(start = 16.dp, top = 20.dp),
            ) {
                AnimatedVisibility(
                    visible = shouldShowShip1.value,
                    enter = slideInHorizontally(
                        spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow,
                        ),
                    ),
                ) {
                    Box(modifier = Modifier.padding(end = 16.dp)) {
                        ShipComposable(icon = R.drawable.baseline_star_24, text = voteAverage)
                    }
                }
                AnimatedVisibility(
                    visible = shouldShowShip2.value,
                    enter = slideInHorizontally(
                        spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow,
                        ),
                    ),
                ) {
                    ShipComposable(icon = R.drawable.baseline_calendar_month_24, text = releaseYear)
                }
            }

            AnimatedVisibility(
                visible = shouldShowInfo.value,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically(),
            ) {
                LazyColumn {
                    item {
                        Text(
                            text = overview,
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Justify,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier.padding(top = 30.dp, start = 16.dp, end = 16.dp),
                        )
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(topGradient) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
        ) {
            TopGradientComposable(
                modifier = Modifier
                    .height(120.dp)
                    .fillMaxWidth(),
            )
            Box(modifier = Modifier.padding(16.dp)) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(MaterialTheme.colorScheme.background, shape = CircleShape),
                ) {
                    IconButton(onClick = onBackPress::invoke) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ShipComposable(@DrawableRes icon: Int, text: String) {
    Row(
        modifier =
        Modifier.background(
            color = MaterialTheme.colorScheme.onBackground,
            shape = RoundedCornerShape(20.dp),
        ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = "ship attribute",
            modifier = Modifier.padding(end = 4.dp, start = 8.dp).size(24.dp)
                .padding(vertical = 4.dp),
            tint = Color.DarkGray,
        )

        Text(
            text = text,
            color = MaterialTheme.colorScheme.background,
            style = MaterialTheme.typography.labelLarge,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(end = 8.dp),
        )
    }
}
