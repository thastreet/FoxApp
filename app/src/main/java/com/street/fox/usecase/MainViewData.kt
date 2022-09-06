package com.street.fox.usecase

data class MainViewData(
    val content: Content
) {
    sealed interface Content {
        object LoginScreen : Content

        data class MainScreen(
            val name: String
        ) : Content
    }
}
