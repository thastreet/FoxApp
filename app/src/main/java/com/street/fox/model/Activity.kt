package com.street.fox.model

import kotlinx.serialization.Serializable

@Serializable
data class Activity(
    val id: Long,
    val name: String
)
