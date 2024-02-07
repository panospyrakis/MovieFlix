package com.spyrakis.movieflix.data.repositories.sources.remote.dto

import com.google.gson.annotations.SerializedName

data class ReviewDto(
    @SerializedName("author") val author: String,
    @SerializedName("content") val content: String
)