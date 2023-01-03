package com.street.fox.usecase

import com.soywiz.klock.DateTimeTz
import com.soywiz.klock.seconds

data class ActivityViewData(
    val id: Long,
    val name: String,
    val startDate: DateTimeTz,
    val elapsedTimeSeconds: Int
) {
    val endDate = startDate + elapsedTimeSeconds.seconds
}
