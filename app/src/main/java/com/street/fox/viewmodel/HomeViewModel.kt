package com.street.fox.viewmodel

import androidx.lifecycle.ViewModel
import com.street.fox.StateData
import com.street.fox.usecase.HomeUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class HomeViewModel(homeUseCase: HomeUseCase) : ViewModel() {
    val username: Flow<String> = homeUseCase.getHomeViewData().map { stateData ->
        when (stateData) {
            is StateData.Data -> stateData.value.username
            is StateData.Error -> {
                when (stateData.exception) {
                    is HomeUseCase.LoggedOutException -> "Logged out"
                    else -> "Error: ${stateData.exception.message}"
                }
            }
            is StateData.Loading -> "Loading"
        }
    }
}
