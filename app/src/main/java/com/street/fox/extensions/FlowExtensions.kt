package com.street.fox.extensions

import com.street.fox.StateData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun <T, U> Flow<StateData<T>>.mapData(transform: (T) -> U): Flow<StateData<U>> =
    map {
        it.mapData(transform)
    }
