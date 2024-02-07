package com.spyrakis.movieflix.features.movielist

sealed class MovieListItem {
    class Movie(val movie: com.spyrakis.movieflix.domain.models.Movie) : MovieListItem()
    data object Loading : MovieListItem()
}