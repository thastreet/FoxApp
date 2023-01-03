package com.street.fox.usecase

import com.street.fox.StateData
import com.street.fox.extensions.mapData
import com.street.fox.repository.TrackRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
class TrackUseCaseImpl(private val trackRepository: TrackRepository) : TrackUseCase {
    override fun getRecentlyPlayed(): Flow<StateData<List<String>>> =
        trackRepository.getRecentlyPlayed().mapData {
            it.items.map { it.track.name }
        }
}
