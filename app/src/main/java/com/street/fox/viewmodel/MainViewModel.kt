package com.street.fox.viewmodel

import androidx.lifecycle.ViewModel
import com.street.fox.usecase.MainUseCase
import kotlinx.coroutines.flow.mapLatest
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class MainViewModel(mainUseCase: MainUseCase) : ViewModel() {
    val content = mainUseCase.getMainViewData().mapLatest { it.content }
}
