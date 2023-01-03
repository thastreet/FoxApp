package com.street.fox

sealed interface StateData<T> {
    val value: T?

    data class Loading<T>(
        override val value: T? = null
    ) : StateData<T>

    data class Data<T>(
        override val value: T? = null
    ) : StateData<T>

    data class Error<T>(
        val exception: Exception,
        override val value: T? = null
    ) : StateData<T>
}
