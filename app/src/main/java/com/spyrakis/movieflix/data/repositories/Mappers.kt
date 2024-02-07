package com.spyrakis.movieflix.data.repositories

import com.spyrakis.movieflix.data.repositories.sources.local.db.entities.MovieEntity
import com.spyrakis.movieflix.data.repositories.sources.remote.dto.CreditsDto
import com.spyrakis.movieflix.data.repositories.sources.remote.dto.GenreDto
import com.spyrakis.movieflix.data.repositories.sources.remote.dto.MovieDetailsDto
import com.spyrakis.movieflix.data.repositories.sources.remote.dto.MovieDto
import com.spyrakis.movieflix.data.repositories.sources.remote.dto.ReviewDto
import com.spyrakis.movieflix.data.repositories.sources.remote.dto.StakeholderDto
import com.spyrakis.movieflix.domain.models.Credits
import com.spyrakis.movieflix.domain.models.Genre
import com.spyrakis.movieflix.domain.models.Movie
import com.spyrakis.movieflix.domain.models.MovieDetails
import com.spyrakis.movieflix.domain.models.Review
import com.spyrakis.movieflix.domain.models.SimilarMovie
import com.spyrakis.movieflix.domain.models.Stakeholder

// dto to domain
fun List<MovieDto>.dtosToMovies() = this.map { it.dtoToMovie() }

fun MovieDto.dtoToMovie() = Movie(
    this.id, this.poster, this.title, this.releaseDate, this.rating, this.popularity
)

fun List<MovieDto>.dtosToSimilarMovies() = this.map { it.dtoToSimilarMovie() }

fun MovieDto.dtoToSimilarMovie() = SimilarMovie(this.poster)

fun MovieDetailsDto.dtoToMovieDetails() = MovieDetails(
    this.id,
    this.poster,
    this.title,
    this.releaseDate,
    this.rating,
    this.overview,
    this.genres.dtosToGenre(),
    this.credits.dtoToCredit(),
    this.homepage,
    this.runtime
)

fun GenreDto.dtoToGenre() = Genre(this.name)
fun List<GenreDto>.dtosToGenre() = this.map { it.dtoToGenre() }

fun CreditsDto.dtoToCredit() = Credits(this.cast.dtosToStakeholder(), this.crew.dtosToStakeholder())


fun StakeholderDto.dtoToStakeholder() = Stakeholder(this.name, this.job)
fun List<StakeholderDto>.dtosToStakeholder() = this.map { it.dtoToStakeholder() }

fun ReviewDto.dtoToReview() = Review(this.author, this.content)
fun List<ReviewDto>.dtosToReviews() = this.map { it.dtoToReview() }

// entity to domain
fun List<MovieEntity>.entitiesToMovies() = this.map { it.entityToMovie() }

fun MovieEntity.entityToMovie() = Movie(
    this.id,
    this.poster,
    this.title,
    this.releaseDate,
    this.rating,
    this.popularity,
    this.isFavourite
)

// domain to entity
fun Movie.movieToEntity() = MovieEntity(
    this.id,
    this.poster,
    this.title,
    this.releaseDate,
    this.rating,
    this.popularity,
    this.isFavourite
)

fun List<Movie>.moviesToEntities() = this.map { it.movieToEntity() }

