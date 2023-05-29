package com.theblackbit.instantflix.core.usecase

import androidx.paging.Pager
import com.theblackbit.instantflix.core.data.model.MovieTvEntity

class RequestPaginatedDataUseCaseImpl(
    private val pagerData: Pager<Int, MovieTvEntity>,
) : RequestPaginatedDataUseCase {
    override val pager: Pager<Int, MovieTvEntity>
        get() = this.pagerData
}
