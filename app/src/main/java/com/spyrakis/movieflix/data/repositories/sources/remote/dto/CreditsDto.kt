package com.spyrakis.movieflix.data.repositories.sources.remote.dto

import com.google.gson.annotations.SerializedName

data class CreditsDto(
    @SerializedName("cast")
    val cast: List<StakeholderDto>,
    @SerializedName("crew")
    val crew: List<StakeholderDto>
)