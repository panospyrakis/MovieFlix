package com.spyrakis.movieflix.domain.models

import com.spyrakis.movieflix.core.IMAGES_BASE_URL

data class SimilarMovie(val poster: String?) {
    fun getPosterUrl(): String = IMAGES_BASE_URL + poster
}
