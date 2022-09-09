package com.street.fox.usecase

import com.street.fox.StateData
import com.street.fox.extensions.mapData
import com.street.fox.repository.AthleteRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
class HomeUseCaseImpl(athleteRepository: AthleteRepository) : HomeUseCase {
    override val viewData: Flow<StateData<HomeViewData>> =
        athleteRepository.getAthlete().mapData {
            HomeViewData(
                "${it.firstname} ${it.lastname}",
                it.profileImageUrl.orEmpty()
            )
        }
}
