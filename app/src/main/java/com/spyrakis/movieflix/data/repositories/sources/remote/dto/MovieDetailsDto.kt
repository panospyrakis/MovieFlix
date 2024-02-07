package com.spyrakis.movieflix.data.repositories.sources.remote.dto

import com.google.gson.annotations.SerializedName

data class MovieDetailsDto(
    @SerializedName("genres")
    val genres: List<GenreDto>,
    @SerializedName("id")
    val id: Int,
    @SerializedName("poster_path")
    val poster: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("vote_average")
    val rating: Double?,
    @SerializedName("credits")
    val credits: CreditsDto,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("homepage")
    val homepage: String?,
    @SerializedName("runtime")
    val runtime: Int
)