package com.street.fox.viewmodel

import com.street.fox.usecase.HomeViewData
import kotlinx.coroutines.flow.StateFlow

interface HomeViewModel {
    val viewData: StateFlow<HomeViewData>
}
