package com.challenge.instantflix.core.usecase

import androidx.paging.Pager
import com.challenge.instantflix.core.data.model.MovieTvEntity

interface RequestPaginatedDataUseCase {
    val pager: Pager<Int, MovieTvEntity>
}
