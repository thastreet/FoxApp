package com.street.fox.usecase

import com.street.fox.StateData
import kotlinx.coroutines.flow.Flow

interface TrackUseCase {
    fun getRecentlyPlayed(): Flow<StateData<List<String>>>
}
