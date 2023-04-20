package com.challenge.instantflix.core.data.model

import com.google.gson.annotations.SerializedName

data class MovieTvResponse(
    val page: Int?,
    val result: List<MovieTv>,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?,
)
