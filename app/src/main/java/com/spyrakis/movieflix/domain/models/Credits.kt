package com.spyrakis.movieflix.domain.models

data class Credits(
    val cast: List<Stakeholder>, val crew: List<Stakeholder>
)