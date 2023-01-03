package com.street.fox.repository

import com.street.fox.StateData
import com.street.fox.StravaApi
import com.street.fox.model.Activity
import com.street.fox.model.Athlete
import com.street.fox.model.UpdatableActivity
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Single

@Single
class AthleteRepository(private val api: StravaApi) {
    fun getAthlete(): Flow<StateData<Athlete>> =
        api.get("/athlete")

    fun getActivities(): Flow<StateData<List<Activity>>> =
        api.get("/athlete/activities")

    fun updateActivity(id: Long, updatableActivity: UpdatableActivity) =
        api.put<Unit>("/activities/$id", Json.parseToJsonElement(Json.encodeToString(UpdatableActivity.serializer(), updatableActivity)))
}
