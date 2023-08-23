@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)

package com.storeapp.lego.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.storeapp.lego.R
import com.storeapp.lego.ui.component.LoadingDialog
import com.storeapp.lego.ui.component.TextFieldIcons
import com.storeapp.lego.ui.component.TextFieldLeftIcon
import com.storeapp.lego.ui.navigate.ScreensRoute
import com.storeapp.lego.ui.screens.viewmodels.LoginViewModel
import com.storeapp.lego.ui.theme.LegoStoreTheme
import com.storeapp.lego.utils.UIState

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onNavigation: (route: String, replace: Boolean) -> Unit
) {

    val stateLogin by viewModel.onLogin.observeAsState()
    var loading by remember { mutableStateOf(false) }
    val snackBarState = remember { SnackbarHostState() }


    when (stateLogin) {
        UIState.Loading -> loading = true
        is UIState.Error -> {
            val err = (stateLogin as UIState.Error)
            LaunchedEffect(key1 = true, block = {
                snackBarState.showSnackbar(
                    message = "${err.title} - ${err.message}",
                    duration = SnackbarDuration.Long
                )
            })
            loading = false
        }

        is UIState.Success<*> -> onNavigation(ScreensRoute.LegoStoreScreen.route, true)

        else -> loading = false
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarState) }
    ) {
        Box(modifier = Modifier.padding(it)) {
            BodyLogin(loading, isSystemInDarkTheme(),
                onLogin = { name, pass ->
                    viewModel.doLogin(name, pass)
                },
                onRegister = { onNavigation(ScreensRoute.RegisterScreen.route, false) }
            )
        }
    }
}

@Composable
fun BodyLogin(
    loading: Boolean = false,
    isDarkTheme: Boolean = false,
    onLogin: (String, String) -> Unit = { _, _ -> },
    onRegister: () -> Unit = {}
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var name by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var darkTheme by remember { mutableStateOf(isDarkTheme) }

    LegoStoreTheme(
        darkTheme = darkTheme
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(color = MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.baseline_lens_blur_24),
                contentDescription = "login",
                modifier = Modifier
                    .size(200.dp)
                    .clip(shape = ShapeDefaults.Large)
                    .padding(bottom = 16.dp, top = 60.dp)
            )


            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(end = 20.dp, top = 40.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Switch(
                        checked = darkTheme,
                        onCheckedChange = { darkTheme = it },
                        colors = SwitchDefaults.colors(
                            uncheckedBorderColor = MaterialTheme.colorScheme.primary,
                            uncheckedThumbColor = MaterialTheme.colorScheme.primary,
                            uncheckedTrackColor = MaterialTheme.colorScheme.background
                        )
                    )
                    Text(
                        text = stringResource(id = R.string.change_theme) + " " +
                                if (darkTheme) stringResource(
                                    id = R.string.txtDarkTheme
                                ) else stringResource(
                                    id = R.string.txtLightTheme
                                ),
                        fontWeight = FontWeight.Bold,
                        fontSize = TextUnit(12f, TextUnitType.Sp),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            TextFieldLeftIcon(
                value = name,
                onChangeText = { name = it },
                placeholder = stringResource(R.string.email),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp, horizontal = 20.dp),
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_person_outline_24),
                        contentDescription = "ic_user"
                    )
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    keyboardType = KeyboardType.Email,
                    autoCorrect = false,
                    imeAction = ImeAction.Next
                )
            )

            TextFieldIcons(
                value = pass,
                onChangeText = { pass = it },
                placeholder = stringResource(R.string.password),
                visualTransformation = if (isPasswordVisible) {
                    VisualTransformation.None
                } else PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_password_24),
                        contentDescription = "ic_pass"
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                        Icon(
                            painter = painterResource(
                                id = if (isPasswordVisible) {
                                    R.drawable.baseline_visibility_off_24
                                } else R.drawable.baseline_visibility_24
                            ), contentDescription = "Toggle password visibility"
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    keyboardType = KeyboardType.Password,
                    autoCorrect = false,
                    imeAction = ImeAction.Done
                )
            )

            Button(
                onClick = {
                    keyboardController?.hide()
                    onLogin(name, pass)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .height(45.dp),
                shape = ShapeDefaults.Medium,
                enabled = name.isNotBlank() && pass.isNotBlank()
            ) {
                Text(
                    text = stringResource(id = R.string.login),
                    fontSize = TextUnit(16f, TextUnitType.Sp)
                )
            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, end = 24.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = stringResource(id = R.string.createAct),
                    fontSize = TextUnit(16f, TextUnitType.Sp),
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable { onRegister() },
                )
            }

            LoadingDialog(loading)
        }
    }
}

@Preview
@Composable
fun PreviewLoginScreen() {
    BodyLogin()
}