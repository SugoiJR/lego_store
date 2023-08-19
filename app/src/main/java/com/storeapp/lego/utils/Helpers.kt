package com.storeapp.lego.utils

import androidx.navigation.NavController

object Helpers {
    fun replaceScreen(navController: NavController, route: String) {
        navController.popBackStack()
        navController.navigate(route){
            launchSingleTop = true
        }
    }
}