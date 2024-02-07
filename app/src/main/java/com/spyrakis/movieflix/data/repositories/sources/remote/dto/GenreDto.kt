package com.spyrakis.movieflix.data.repositories.sources.remote.dto

import com.google.gson.annotations.SerializedName

data class GenreDto(
    @SerializedName("name") val name: String
)