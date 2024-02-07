package com.spyrakis.movieflix.features.movielist


import com.spyrakis.movieflix.core.navcontroller.Destination
import com.spyrakis.movieflix.core.navcontroller.Navigator

fun interface MovieListRouter {
    fun navToDetails(id: Int)
}

fun Navigator.asMovieListRouter() = MovieListRouter {
    navigate(Destination.Details(it))
}