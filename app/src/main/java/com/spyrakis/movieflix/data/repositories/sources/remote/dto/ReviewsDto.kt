package com.spyrakis.movieflix.data.repositories.sources.remote.dto

import com.google.gson.annotations.SerializedName


data class ReviewsDto(
    @SerializedName("results")
    val reviews: List<ReviewDto>
)