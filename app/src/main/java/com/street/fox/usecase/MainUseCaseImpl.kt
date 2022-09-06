package com.street.fox.usecase

import com.street.fox.repository.Token
import com.street.fox.repository.TokenRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import org.koin.core.annotation.Factory

@Factory
class MainUseCaseImpl(private val tokenRepository: TokenRepository) : MainUseCase {
    override fun getMainViewData(): Flow<MainViewData> =
        tokenRepository.token.mapLatest {
            MainViewData(
                it !is Token.Value
            )
        }
}
