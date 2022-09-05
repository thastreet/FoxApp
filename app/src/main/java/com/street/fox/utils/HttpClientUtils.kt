package com.street.fox.utils

import android.util.Log
import com.street.fox.Const
import com.street.fox.Token
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

private const val HTTP_TIMEOUT = 100_000

fun createHttpClient(getToken: () -> Token, setToken: (token: Token) -> Unit): HttpClient =
    HttpClient(Android) {
        expectSuccess = true

        engine {
            connectTimeout = HTTP_TIMEOUT
            socketTimeout = HTTP_TIMEOUT
        }

        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.HEADERS
            filter { request ->
                request.url.host.contains("ktor.io")
            }
        }

        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                }
            )
        }

        install(Auth) {
            bearer {
                loadTokens {
                    (getToken() as? Token.Value)?.let {
                        BearerTokens(it.accessToken, it.refreshToken)
                    }
                }

                refreshTokens {
                    try {
                        val response: RefreshTokenResponse = client.get("${Const.BASE_API_URL}/refresh?refresh_token=${oldTokens?.refreshToken}").body()
                        val accessToken = response.accessToken
                        val refreshToken = response.refreshToken

                        if (response.status == "success" && accessToken != null && refreshToken != null) {
                            Log.i("Refresh token", "Token refreshed successfully")
                            setToken(Token.Value(accessToken, refreshToken))
                            BearerTokens(accessToken, refreshToken)
                        } else {
                            Log.e("Refresh token", "Error status: ${response.status}")
                            setToken(Token.NotSet)
                            null
                        }
                    } catch (e: Exception) {
                        Log.e("Refresh token", "Unhandled exception: ${e.message.orEmpty()}")
                        setToken(Token.NotSet)
                        null
                    }
                }
            }
        }
    }

@Serializable
private data class RefreshTokenResponse(
    val status: String,
    val accessToken: String?,
    val refreshToken: String?
)
