package com.storeapp.lego.ui.component

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.storeapp.lego.ui.navigate.ScreensRoute
import com.storeapp.lego.ui.screens.DetailLegoScreen
import com.storeapp.lego.ui.screens.LegosScreen
import com.storeapp.lego.ui.screens.LoginScreen
import com.storeapp.lego.ui.screens.RegisterScreen
import com.storeapp.lego.ui.screens.SplashScreen
import com.storeapp.lego.utils.Helpers.replaceScreen


@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ScreensRoute.SplashScreen.route
    ) {
        composable(ScreensRoute.SplashScreen.route) {
            SplashScreen {
                replaceScreen(navController, it)
            }
        }
        composable(ScreensRoute.LoginScreen.route) {
            LoginScreen { route, replace ->
                when (replace) {
                    true -> replaceScreen(navController, route)
                    false -> navController.navigate(route) {
                        launchSingleTop = true
                    }
                }
            }
        }
        composable(ScreensRoute.RegisterScreen.route) {
            RegisterScreen(navController = navController)
        }
        composable(ScreensRoute.LegoStoreScreen.route) {
            LegosScreen(navController = navController)
        }
        composable(
            route = ScreensRoute.DetailLegoScreen.route + "/{legoId}",
            arguments = listOf(navArgument(name = "legoId") { type = NavType.StringType })
        ) { backStackEntry ->
            DetailLegoScreen(
                navController = navController,
                legoId = backStackEntry.arguments?.getString("legoId") ?: ""
            )
        }
    }
}