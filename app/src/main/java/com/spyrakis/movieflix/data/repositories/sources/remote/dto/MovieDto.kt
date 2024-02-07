package com.spyrakis.movieflix.data.repositories.sources.remote.dto

import com.google.gson.annotations.SerializedName

class MovieDto(
    @SerializedName("id") val id: Int,
    @SerializedName("poster_path") val poster: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("vote_average") val rating: Double?,
    @SerializedName("popularity") val popularity: Double?
)