package com.spyrakis.movieflix.features.movielist

sealed interface MovieListState {
    data object Initial : MovieListState
    data object Error : MovieListState
    data class Success(val list: List<MovieListItem>) : MovieListState
}