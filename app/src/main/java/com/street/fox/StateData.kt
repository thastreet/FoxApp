package com.street.fox

sealed interface StateData<T> {
    class Loading<T> : StateData<T>

    data class Data<T>(
        val value: T
    ) : StateData<T>

    data class Error<T>(
        val exception: Exception
    ) : StateData<T>
}
