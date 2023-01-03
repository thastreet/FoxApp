package com.street.fox.usecase

import com.street.fox.StateData
import kotlinx.coroutines.flow.Flow

interface ActivityUseCase {
    fun updateActivity(id: Long, description: String): Flow<StateData<Unit>>
}
