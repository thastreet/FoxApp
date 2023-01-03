package com.street.fox.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecentlyPlayed(
    val items: List<Item>
) {
    @Serializable
    data class Item(
        val track: Track,
        @SerialName("played_at") val playedAt: String
    ) {
        @Serializable
        data class Track(
            val name: String,
            @SerialName("duration_ms") val durationMs: Long
        )
    }
}
