package com.street.fox.repository

import android.content.SharedPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Single

@Single
class TokenRepository(private val sharedPreferences: SharedPreferences) {
    companion object {
        private const val STRAVA_TOKEN_KEY = "STRAVA_TOKEN_KEY"
    }

    private val stravaToken: MutableStateFlow<Token> = MutableStateFlow(getStravaToken())

    fun getStravaToken(): Token =
        sharedPreferences
            .getString(STRAVA_TOKEN_KEY, null)
            .takeUnless { it.isNullOrEmpty() }
            ?.let { token -> Json.decodeFromString(Token.serializer(), token) }
            ?: Token.NotSet

    fun setStravaToken(token: Token) {
        sharedPreferences
            .edit()
            .putString(STRAVA_TOKEN_KEY, Json.encodeToString(Token.serializer(), token))
            .apply()
        this.stravaToken.value = token
    }

    val isCurrentlyLoggedIn: Boolean
        get() = stravaToken.value is Token.Value

    val isLoggedIn: Flow<Boolean> =
        stravaToken.mapLatest { it is Token.Value }
}
