package com.street.fox.usecase

data class HomeViewData(
    val name: String,
    val profileImageUrl: String,
    val activities: List<ActivityViewData>
)
