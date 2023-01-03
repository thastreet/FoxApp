package com.street.fox.usecase

import android.net.Uri
import com.street.fox.Screen
import kotlinx.coroutines.flow.Flow

interface MainUseCase {
    val initialScreen: Screen
    val isLoggedIn: Flow<Boolean>
    fun handleLoginUri(uri: Uri, proceed: () -> Unit)
}
