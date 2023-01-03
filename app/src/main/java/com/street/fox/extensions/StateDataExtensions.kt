package com.street.fox.extensions

import com.street.fox.StateData

fun <T, U> StateData<T>.mapData(transform: (T?) -> U): StateData<U> =
    when (this) {
        is StateData.Data -> StateData.Data(transform(value))
        is StateData.Error -> StateData.Error(exception)
        is StateData.Loading -> StateData.Loading()
    }
