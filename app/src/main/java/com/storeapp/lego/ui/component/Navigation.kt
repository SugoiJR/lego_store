package com.storeapp.lego.ui.component

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
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
import com.storeapp.lego.ui.screens.viewmodels.DetLegoViewModel
import com.storeapp.lego.ui.screens.viewmodels.LegoViewModel
import com.storeapp.lego.ui.screens.viewmodels.LoginViewModel


@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ScreensRoute.SplashScreen.route
    ) {
        composable(ScreensRoute.SplashScreen.route) {
            SplashScreen(navController)
        }
        composable(ScreensRoute.LoginScreen.route) {
            val viewModel = hiltViewModel<LoginViewModel>()
            LoginScreen(viewModel, navController)
        }
        composable(ScreensRoute.RegisterScreen.route) {
            val viewModel = hiltViewModel<LoginViewModel>()
            RegisterScreen(navController, viewModel)
        }
        composable(ScreensRoute.LegoStoreScreen.route) {
            val viewModel = hiltViewModel<LegoViewModel>()
            LegosScreen(viewModel, navController)
        }
        composable(
            route = ScreensRoute.DetailLegoScreen.route + "/{legoId}",
            arguments = listOf(navArgument(name = "legoId") { type = NavType.StringType })
        ) { backStackEntry ->
            val viewModel = hiltViewModel<DetLegoViewModel>()
            DetailLegoScreen(
                viewModel,
                navController,
                legoId = backStackEntry.arguments?.getString("legoId") ?: ""
            )
        }
    }
}