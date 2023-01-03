package com.street.fox

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.JsonElement
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Named

@Factory
class StravaApi(@Named("strava") val httpClient: HttpClient) {
    inline fun <reified T> get(path: String): Flow<StateData<T>> =
        flow {
            emit(StateData.Loading())

            try {
                emit(StateData.Data(httpClient.get(Const.BASE_STRAVA_API_URL + path).body()))
            } catch (e: Exception) {
                emit(StateData.Error(e))
            }
        }

    inline fun <reified T> put(path: String, body: JsonElement): Flow<StateData<T>> =
        flow {
            emit(StateData.Loading())

            try {
                emit(StateData.Data(httpClient.put(Const.BASE_STRAVA_API_URL + path) {
                    contentType(ContentType.Application.Json)
                    setBody(body)
                }.body()))
            } catch (e: Exception) {
                emit(StateData.Error(e))
            }
        }
}
