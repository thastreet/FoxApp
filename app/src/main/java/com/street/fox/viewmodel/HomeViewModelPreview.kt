package com.street.fox.viewmodel

import com.street.fox.usecase.ActivityViewData
import com.street.fox.usecase.HomeViewData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModelPreview : HomeViewModel {
    override val viewData: StateFlow<HomeViewData> = MutableStateFlow(
        HomeViewData(
            "Mathieu Larue",
            "",
            listOf(
                ActivityViewData(123, "Afternoon activity")
            )
        )
    )

    override fun onActivityTapped(activityViewData: ActivityViewData) {}
}
