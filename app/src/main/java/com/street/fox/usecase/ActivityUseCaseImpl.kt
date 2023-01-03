package com.street.fox.usecase

import com.street.fox.StateData
import com.street.fox.model.UpdatableActivity
import com.street.fox.repository.AthleteRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
class ActivityUseCaseImpl(private val athleteRepository: AthleteRepository) : ActivityUseCase {
    override fun updateActivity(id: Long, description: String): Flow<StateData<Unit>> =
        athleteRepository.updateActivity(id, UpdatableActivity(description))
}
