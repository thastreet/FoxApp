package com.street.fox

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Named

@Factory
class StravaApi(@Named("strava") val httpClient: HttpClient) {
    inline fun <reified T> request(path: String): Flow<StateData<T>> =
        flow {
            emit(StateData.Loading())

            try {
                emit(StateData.Data(httpClient.get(Const.BASE_STRAVA_API_URL + path).body()))
            } catch (e: Exception) {
                emit(StateData.Error(e))
            }
        }
}
