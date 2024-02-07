package com.spyrakis.movieflix.domain.models

import com.spyrakis.movieflix.core.IMAGES_BASE_URL

data class MovieDetails(
    val id: Int,
    val poster: String?,
    val title: String?,
    val releaseDate: String?,
    val rating: Double?,
    val overview: String,
    val genres: List<Genre>,
    val credits: Credits,
    val homepage: String?,
    val runtime: Int
) {
    fun getPosterUrl(): String = IMAGES_BASE_URL + poster

}