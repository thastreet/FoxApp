package com.street.fox.usecase

import com.street.fox.StateData
import com.street.fox.repository.AthleteRepository
import com.street.fox.repository.TokenRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.mapLatest
import org.koin.core.annotation.Factory

@Factory
class MainUseCaseImpl(private val tokenRepository: TokenRepository, private val athleteRepository: AthleteRepository) : MainUseCase {
    override fun getMainViewData(): Flow<MainViewData> =
        tokenRepository.isLoggedIn.flatMapLatest { isLoggedIn ->
            when (isLoggedIn) {
                true -> athleteRepository.getAthlete().mapLatest { stateData ->
                    MainViewData(
                        MainViewData.Content.MainScreen(
                            when (stateData) {
                                is StateData.Data -> "${stateData.value.firstname} ${stateData.value.lastname}"
                                is StateData.Error,
                                is StateData.Loading -> ""
                            }
                        )
                    )
                }
                false -> flowOf(MainViewData(MainViewData.Content.LoginScreen))
            }
        }
}
