package com.street.fox.extensions

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptionsBuilder

fun NavOptionsBuilder.popUpToStart(navController: NavController) {
    popUpTo(navController.graph.findStartDestination().id) {
        inclusive = true
    }
}
