package com.street.fox.repository

import android.content.SharedPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Single

@Single
class TokenRepository(private val sharedPreferences: SharedPreferences) {
    companion object {
        private const val TOKEN_KEY = "TOKEN_KEY"
    }

    private val internalToken: MutableStateFlow<Token> = MutableStateFlow(getToken())
    val token: Flow<Token> = internalToken

    fun getToken(): Token =
        sharedPreferences
            .getString(TOKEN_KEY, null)
            .takeUnless { it.isNullOrEmpty() }
            ?.let { token -> Json.decodeFromString(Token.serializer(), token) }
            ?: Token.NotSet

    fun setToken(token: Token) {
        sharedPreferences
            .edit()
            .putString(TOKEN_KEY, Json.encodeToString(Token.serializer(), token))
            .apply()
        internalToken.value = token
    }
}
