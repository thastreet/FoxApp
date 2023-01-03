package com.street.fox.model

import kotlinx.serialization.Serializable

@Serializable
data class RecentlyPlayed(
    val items: List<Item>
) {
    @Serializable
    data class Item(
        val track: Track
    ) {
        @Serializable
        data class Track(
            val name: String
        )
    }
}
