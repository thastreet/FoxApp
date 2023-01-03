package com.street.fox.usecase

import android.net.Uri
import com.street.fox.Screen
import com.street.fox.repository.Token
import com.street.fox.repository.TokenRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
class MainUseCaseImpl(private val tokenRepository: TokenRepository) : MainUseCase {
    companion object {
        private const val STATE_QUERY_PARAM = "state"
        private const val RESULT_QUERY_PARAM = "result"
        private const val ACCESS_TOKEN_QUERY_PARAM = "access_token"
        private const val REFRESH_TOKEN_QUERY_PARAM = "refresh_token"
    }

    override val initialScreen: Screen = when (tokenRepository.isCurrentlyLoggedIn) {
        true -> Screen.HOME
        false -> Screen.LOGIN
    }

    override val isLoggedIn: Flow<Boolean> = tokenRepository.isLoggedIn

    override fun handleLoginUri(uri: Uri, proceed: () -> Unit) {
        val state = uri.getQueryParameter(STATE_QUERY_PARAM)
        val result = uri.getQueryParameter(RESULT_QUERY_PARAM)
        val accessToken = uri.getQueryParameter(ACCESS_TOKEN_QUERY_PARAM)
        val refreshToken = uri.getQueryParameter(REFRESH_TOKEN_QUERY_PARAM)

        if (state == "strava" && result == "success" && accessToken != null && refreshToken != null) {
            tokenRepository.setStravaToken(Token.Value(accessToken, refreshToken))
        } else if (state == "spotify" && result == "success" && accessToken != null && refreshToken != null) {
            tokenRepository.setSpotifyToken(Token.Value(accessToken, refreshToken))
        }

        if (tokenRepository.getStravaToken() !is Token.NotSet && tokenRepository.getSpotifyToken() !is Token.NotSet) {
            proceed()
        }
    }
}
