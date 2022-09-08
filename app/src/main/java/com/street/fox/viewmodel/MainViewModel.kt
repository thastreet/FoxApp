package com.street.fox.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.street.fox.Screen
import com.street.fox.usecase.MainUseCase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class MainViewModel(private val mainUseCase: MainUseCase) : ViewModel() {
    interface NavListener {
        fun navigateToHome()
        fun navigateToLogin()
    }

    var navListener: NavListener? = null

    val initialScreen: Screen = mainUseCase.initialScreen

    init {
        viewModelScope.launch {
            mainUseCase.isLoggedIn
                .distinctUntilChanged()
                .collectLatest { isLoggedIn ->
                    if (!isLoggedIn) {
                        navListener?.navigateToLogin()
                    }
                }
        }
    }

    fun handleLoginUri(uri: Uri) {
        if (mainUseCase.handleLoginUri(uri)) {
            navListener?.navigateToHome()
        }
    }
}
