package com.spyrakis.movieflix.domain.usecases

import com.spyrakis.movieflix.domain.repositories.MovieRepository
import javax.inject.Inject

class RefreshItemsUseCase @Inject constructor(private val movieRepository: MovieRepository) {

    suspend operator fun invoke(){
        movieRepository.deleteAll()
    }
}