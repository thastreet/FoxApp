package com.street.fox.usecase

import com.soywiz.klock.DateTime
import com.street.fox.StateData
import com.street.fox.repository.AthleteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import org.koin.core.annotation.Factory

@Factory
class HomeUseCaseImpl(athleteRepository: AthleteRepository) : HomeUseCase {
    override val viewData: Flow<StateData<HomeViewData>> =
        athleteRepository.getAthlete().combine(athleteRepository.getActivities()) { athleteStateData, activitiesStateData ->
            if (athleteStateData is StateData.Loading && activitiesStateData is StateData.Loading) return@combine StateData.Loading()

            val athlete = athleteStateData.value
            val activities = activitiesStateData.value

            if (athlete != null && activities != null) {
                StateData.Data(
                    HomeViewData(
                        "${athlete.firstname} ${athlete.lastname}",
                        athlete.profileImageUrl.orEmpty(),
                        activities.map { ActivityViewData(it.id, it.name, DateTime.parse(it.startDate), it.elapsedTimeSeconds) }
                    )
                )
            } else {
                StateData.Error(Exception((athleteStateData as? StateData.Error)?.exception?.message ?: (activitiesStateData as? StateData.Error)?.exception?.message))
            }
        }
}
