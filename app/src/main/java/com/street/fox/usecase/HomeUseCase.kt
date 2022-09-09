package com.street.fox.usecase

import com.street.fox.StateData
import kotlinx.coroutines.flow.Flow

interface HomeUseCase {
    val viewData: Flow<StateData<HomeViewData>>
}
