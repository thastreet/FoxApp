package com.street.fox.home

import com.street.fox.AthleteRepository
import com.street.fox.StateData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Factory

@Factory
class HomeUseCase(private val athleteRepository: AthleteRepository) {
    class LoggedOutException : Exception()

    fun getHomeViewData(): Flow<StateData<HomeViewData>> =
        athleteRepository.getAthlete().map { stateData ->
            when (stateData) {
                is StateData.Data -> StateData.Data(HomeViewData("${stateData.value.firstname} ${stateData.value.lastname}"))
                is StateData.Error -> StateData.Error(stateData.exception)
                is StateData.Loading -> StateData.Loading()
            }
        }
}
