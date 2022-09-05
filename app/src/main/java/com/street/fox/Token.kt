package com.street.fox

import kotlinx.serialization.Serializable

@Serializable
sealed interface Token {

    companion object {
        val default = NotSet
    }

    @Serializable
    data class Value(
        val accessToken: String,
        val refreshToken: String
    ) : Token

    @Serializable
    object NotSet : Token
}
