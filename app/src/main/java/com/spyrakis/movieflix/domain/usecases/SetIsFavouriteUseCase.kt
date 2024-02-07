package com.spyrakis.movieflix.domain.usecases

import com.spyrakis.movieflix.domain.repositories.MovieRepository
import javax.inject.Inject

class SetIsFavouriteUseCase @Inject constructor(private val movieRepository: MovieRepository) {

    suspend operator fun invoke(id: Int, isFavourite: Boolean) = movieRepository.setIsFavourite(id, !isFavourite)
}