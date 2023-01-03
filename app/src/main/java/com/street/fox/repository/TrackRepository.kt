package com.street.fox.repository

import com.soywiz.klock.DateTime
import com.street.fox.SpotifyApi
import com.street.fox.StateData
import com.street.fox.model.RecentlyPlayed
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single

@Single
class TrackRepository(private val api: SpotifyApi) {
    fun getRecentlyPlayed(endDate: DateTime): Flow<StateData<RecentlyPlayed>> =
        api.request("/me/player/recently-played?limit=50&before=${endDate.unixMillisLong}")
}
