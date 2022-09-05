package com.street.fox.api

import kotlinx.serialization.Serializable

@Serializable
data class Athlete(
    val username: String?,
    val firstname: String,
    val lastname: String
)
