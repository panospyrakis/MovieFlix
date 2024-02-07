package com.spyrakis.movieflix.data.repositories.sources.local.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "movie")
class MovieEntity(
    @PrimaryKey val id: Int,
    val poster: String?,
    val title: String?,
    val releaseDate: String?,
    val rating: Double?,
    val popularity: Double?,
    val isFavourite: Boolean = false
)