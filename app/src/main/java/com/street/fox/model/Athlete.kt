package com.street.fox.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Athlete(
    val username: String?,
    val firstname: String,
    val lastname: String,
    @SerialName("profile") val profileImageUrl: String?
)
