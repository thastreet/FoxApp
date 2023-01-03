package com.street.fox.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Activity(
    val id: Long,
    val name: String,
    @SerialName("start_date") val startDate: String,
    @SerialName("elapsed_time") val elapsedTimeSeconds: Int
)
