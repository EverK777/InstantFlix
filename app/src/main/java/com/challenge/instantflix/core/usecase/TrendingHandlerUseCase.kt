package com.challenge.instantflix.core.usecase

import com.challenge.instantflix.core.data.model.MovieTvEntity
import com.challenge.instantflix.core.data.model.TypeRequest

interface TrendingHandlerUseCase {
    suspend fun getTrendingMovieTvShow(typeRequest: TypeRequest): MovieTvEntity?
}
