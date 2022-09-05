package com.street.fox

import com.street.fox.api.Api
import com.street.fox.api.Athlete
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single

@Single
class AthleteRepository(private val api: Api) {
    fun getAthlete(): Flow<StateData<Athlete>> =
        api.request("/athlete")
}
