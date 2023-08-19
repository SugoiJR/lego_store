@file:OptIn(ExperimentalMaterial3Api::class)

package com.storeapp.lego.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.storeapp.lego.R
import com.storeapp.lego.domain.model.RegisterModel
import com.storeapp.lego.ui.component.LoadingDialog
import com.storeapp.lego.ui.component.TextFieldLeftIcon
import com.storeapp.lego.ui.navigate.ScreensRoute
import com.storeapp.lego.ui.screens.viewmodels.LoginViewModel
import com.storeapp.lego.utils.Helpers.replaceScreen
import com.storeapp.lego.utils.UIState
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(navController: NavHostController, viewModel: LoginViewModel) {
    val state by viewModel.onRegister.observeAsState()
    var loading by remember { mutableStateOf(false) }

    val snackBarState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    loading = when (state) {
        is UIState.Loading -> true
        is UIState.Error -> {
            LaunchedEffect(key1 = true, block = {
                snackBarState.showSnackbar(
                    message = (state as UIState.Error).title,
                    duration = SnackbarDuration.Long
                )
            })
            false
        }

        is UIState.Success<*> -> {
            navController.popBackStack()
            replaceScreen(navController, ScreensRoute.LegoStoreScreen.route)
            false
        }

        null -> false
    }

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.createAct))
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "back")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackBarState) }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues = paddingValues)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = 20.dp),
            ) {
                TextFieldLeftIcon(
                    value = firstName,
                    onChangeText = { firstName = it },
                    placeholder = stringResource(id = R.string.firstName),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextFieldLeftIcon(
                    value = lastName,
                    onChangeText = { lastName = it },
                    placeholder = stringResource(id = R.string.lastName),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextFieldLeftIcon(
                    value = email,
                    onChangeText = { email = it },
                    placeholder = stringResource(id = R.string.email),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextFieldLeftIcon(
                    value = password,
                    onChangeText = { password = it },
                    placeholder = stringResource(id = R.string.password),
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (password.length < 7) {
                            scope.launch {
                                snackBarState.showSnackbar(
                                    message = "La contraseña debe tener al menos 7 caracteres",
                                    duration = SnackbarDuration.Indefinite,
                                    actionLabel = "Ok"
                                )
                            }
                            return@Button
                        }
                        viewModel.doRegister(
                            RegisterModel(
                                firstName, lastName,
                                email, password,
                            )
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    enabled = (firstName.isNotEmpty() &&
                            lastName.isNotEmpty() &&
                            email.isNotEmpty() &&
                            password.isNotEmpty())
                ) {
                    Text(text = stringResource(id = R.string.createAct))
                }
            }
        }
        LoadingDialog(loading)
    }

}