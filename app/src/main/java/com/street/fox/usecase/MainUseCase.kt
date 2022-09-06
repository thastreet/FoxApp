package com.street.fox.usecase

import kotlinx.coroutines.flow.Flow

interface MainUseCase {
    fun getMainViewData(): Flow<MainViewData>
}
