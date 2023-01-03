package com.street.fox.usecase

import com.soywiz.klock.DateTimeTz

data class RecentlyPlayedViewData(
    val tracks: List<Track>
) {
    data class Track(
        val name: String,
        val durationMs: Long,
        val playedAt: DateTimeTz
    )
}
