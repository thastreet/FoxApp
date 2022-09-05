package com.street.fox.usecase

import com.street.fox.repository.AthleteRepository
import com.street.fox.StateData
import com.street.fox.extensions.mapData
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
class HomeUseCase(private val athleteRepository: AthleteRepository) {
    class LoggedOutException : Exception()

    fun getHomeViewData(): Flow<StateData<HomeViewData>> =
        athleteRepository.getAthlete().mapData { athlete ->
            HomeViewData("${athlete.firstname} ${athlete.lastname}")
        }
}
