package com.spyrakis.movieflix.data.repositories.sources.remote.dto

import com.google.gson.annotations.SerializedName

data class StakeholderDto(
    @SerializedName("name") val name: String, @SerializedName("job") val job: String
)