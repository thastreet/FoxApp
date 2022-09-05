package com.street.fox.repository

import com.street.fox.Api
import com.street.fox.StateData
import com.street.fox.model.Athlete
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single

@Single
class AthleteRepository(private val api: Api) {
    fun getAthlete(): Flow<StateData<Athlete>> =
        api.request("/athlete")
}