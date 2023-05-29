package com.theblackbit.instantflix.core.usecase

import androidx.paging.Pager
import com.theblackbit.instantflix.core.data.model.MovieTvEntity

interface RequestPaginatedDataUseCase {
    val pager: Pager<Int, MovieTvEntity>
}
