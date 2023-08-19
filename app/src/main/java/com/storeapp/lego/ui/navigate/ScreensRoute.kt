package com.storeapp.lego.ui.navigate

sealed class ScreensRoute(val route: String) {
    data object SplashScreen : ScreensRoute("splash_screen")
    data object LoginScreen : ScreensRoute("login_screen")
    data object RegisterScreen : ScreensRoute("register_screen")
    data object LegoStoreScreen : ScreensRoute("lego_store_screen")
    data object DetailLegoScreen : ScreensRoute("detail_lego_screen")
}