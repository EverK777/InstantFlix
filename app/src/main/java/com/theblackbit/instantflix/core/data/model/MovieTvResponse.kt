package com.theblackbit.instantflix.core.data.model

import com.google.gson.annotations.SerializedName

data class MovieTvResponse(
    val page: Int?,
    @SerializedName("results")
    val result: List<MovieTv>,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?,
)
