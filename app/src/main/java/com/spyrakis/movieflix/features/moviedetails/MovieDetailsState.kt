package com.spyrakis.movieflix.features.moviedetails

import com.spyrakis.movieflix.domain.models.MovieDetails
import com.spyrakis.movieflix.domain.models.Review
import com.spyrakis.movieflix.domain.models.SimilarMovie

sealed interface MovieDetailsState {
    data object Initial : MovieDetailsState
    sealed interface Error : MovieDetailsState {
        data object Details : Error
        data object Reviews : Error
        data object Similar : Error
    }

    sealed interface Success : MovieDetailsState {
        data class Favourite(val isFavourite: Boolean) : Success
        data class Details(val details: MovieDetails) : Success
        data class Reviews(val reviews: List<Review>) : Success
        data class Similar(val similar: List<SimilarMovie>) : Success
    }
}