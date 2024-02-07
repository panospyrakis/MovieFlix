package com.spyrakis.movieflix.domain.usecases

import com.spyrakis.movieflix.domain.common.mapSuccess
import com.spyrakis.movieflix.domain.common.DataResult
import com.spyrakis.movieflix.domain.models.Review
import com.spyrakis.movieflix.domain.repositories.MovieRepository
import javax.inject.Inject

class GetReviewsUseCase @Inject constructor(private val movieRepository: MovieRepository) {

    suspend operator fun invoke(id: Int): DataResult<List<Review>> =
        movieRepository.getReviews(id).mapSuccess { this.subList(0, 3) }
}