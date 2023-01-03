package com.street.fox.usecase

import com.street.fox.StateData
import kotlinx.coroutines.flow.Flow

interface TrackUseCase {
    fun getRecentlyPlayed(activityViewData: ActivityViewData): Flow<StateData<RecentlyPlayedViewData>>
}
