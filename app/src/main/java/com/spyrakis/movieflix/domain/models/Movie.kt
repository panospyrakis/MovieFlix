package com.spyrakis.movieflix.domain.models

import com.spyrakis.movieflix.core.IMAGES_BASE_URL


data class Movie(
    val id: Int,
    val poster: String?,
    val title: String?,
    val releaseDate: String?,
    val rating: Double?,
    val popularity: Double?,
    val isFavourite: Boolean = false
) {

    fun getPosterUrl(): String = IMAGES_BASE_URL + poster
}