package com.theblackbit.instantflix.core.usecase

import com.theblackbit.instantflix.core.data.model.MovieTvEntity
import com.theblackbit.instantflix.core.data.model.TypeRequest

interface TrendingHandlerUseCase {
    suspend fun getTrendingMovieTvShow(typeRequest: TypeRequest): MovieTvEntity?
}
