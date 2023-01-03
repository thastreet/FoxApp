package com.street.fox.repository

import com.street.fox.StateData
import com.street.fox.StravaApi
import com.street.fox.model.Activity
import com.street.fox.model.Athlete
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single

@Single
class AthleteRepository(private val api: StravaApi) {
    fun getAthlete(): Flow<StateData<Athlete>> =
        api.request("/athlete")

    fun getActivities(): Flow<StateData<List<Activity>>> =
        api.request("/athlete/activities")
}
