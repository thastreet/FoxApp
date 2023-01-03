package com.street.fox.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.street.fox.extensions.data
import com.street.fox.usecase.HomeUseCase
import com.street.fox.usecase.HomeViewData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class HomeViewModelImpl(homeUseCase: HomeUseCase) : ViewModel(), HomeViewModel {
    private val internalViewData = MutableStateFlow(HomeViewData("", "", emptyList()))
    override val viewData: StateFlow<HomeViewData> = internalViewData

    init {
        viewModelScope.launch {
            homeUseCase.viewData.data().collect {
                internalViewData.value = it
            }
        }
    }
}
