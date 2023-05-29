package com.theblackbit.instantflix.presentation.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.theblackbit.instantflix.core.data.model.MovieTvEntity

@Composable
fun ListPagerComposable(
    title: String,
    pagingItems: LazyPagingItems<MovieTvEntity>,
    onItemClick: (MovieTvEntity) -> Unit,
) {
    val listState = rememberLazyListState()

    Column(
        modifier = Modifier.fillMaxWidth()
            .height(320.dp),
    ) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.W700,
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp),
        )

        LazyRow(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            contentPadding = PaddingValues(horizontal = 8.dp),
            state = listState,
            modifier = Modifier.testTag(title),
        ) {
            items(pagingItems) { movieTvEntity ->
                movieTvEntity?.let {
                    ItemMovieTvShow(movieTv = movieTvEntity, onItemClick = onItemClick::invoke)
                }
            }
        }
    }
}
