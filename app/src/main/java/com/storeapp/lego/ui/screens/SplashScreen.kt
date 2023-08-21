package com.storeapp.lego.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.storeapp.lego.R
import com.storeapp.lego.ui.navigate.ScreensRoute
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onNavigationSwitch: (route: String) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Image(
            painter = painterResource(id = R.drawable.baseline_lens_blur_24),
            contentDescription = "splash",
            modifier = Modifier
                .size(200.dp)
                .clip(shape = ShapeDefaults.Large)
        )

        LaunchedEffect(key1 = true, block = {
            delay(2000)
            val auth = FirebaseAuth.getInstance().currentUser

            if (auth != null) {
                onNavigationSwitch(ScreensRoute.LegoStoreScreen.route)
            } else {
                onNavigationSwitch(ScreensRoute.LoginScreen.route)
            }
        })
    }
}