@file:OptIn(ExperimentalMaterial3Api::class)

package com.storeapp.lego.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.storeapp.lego.R
import com.storeapp.lego.domain.model.LegoModel
import com.storeapp.lego.ui.component.DialogBuyLego
import com.storeapp.lego.ui.component.EmptyBody
import com.storeapp.lego.ui.component.LegoItem
import com.storeapp.lego.ui.component.LoadingDialog
import com.storeapp.lego.ui.component.TopBarShoppingCar
import com.storeapp.lego.ui.navigate.ScreensRoute
import com.storeapp.lego.ui.screens.viewmodels.LegoViewModel
import com.storeapp.lego.utils.UiLisState
import kotlinx.coroutines.launch

@Composable
fun LegosScreen(
    viewModel: LegoViewModel = hiltViewModel(),
    onNav: (route: String, replace: Boolean) -> Unit
) {
    val loading by viewModel.loading.observeAsState(initial = false)
    val cart by viewModel.cart.collectAsState(initial = emptyList())

    val snackBarState = remember { SnackbarHostState() }
    var buyDialog by remember { mutableStateOf(false) }

    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val uiState by produceState<UiLisState>(
        initialValue = UiLisState.Loading, key1 = lifecycle, key2 = viewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            viewModel.uiState.collect { value = it }
        }
    }

    Scaffold(
        topBar = {
            TopBarShoppingCar(itemsCart = cart,
                displayCartDialog = { buyDialog = true },
                options = {
                    IconButton(onClick = {
                        viewModel.logout()
                        onNav(ScreensRoute.SplashScreen.route, true)
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_logout_24),
                            contentDescription = "shopping_cart"
                        )
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackBarState) },
        content = { paddingValues ->
            when (uiState) {
                UiLisState.Loading -> {
                    FlowLoading(paddingValues)
                }

                is UiLisState.Error -> EmptyBody(paddingValues)
                is UiLisState.Success -> {
                    val list = (uiState as UiLisState.Success).data

                    if (list.isNotEmpty()) {
                        BodyLego(paddingValues, list,
                            addCar = {
                                if (it.stock == 0) {
                                    scope.launch {
                                        snackBarState.showSnackbar(
                                            message = context.getString(R.string.empty_stock),
                                            duration = SnackbarDuration.Short
                                        )
                                    }
                                    return@BodyLego
                                }
                                viewModel.addCart(it)
                            },
                            navigateDetail = {
                                onNav(ScreensRoute.DetailLegoScreen.route + "/$it", false)
                            }
                        )
                    } else {
                        EmptyBody(paddingValues)
                        LaunchedEffect(true) {
                            viewModel.getProducts()
                        }
                    }

                }
            }
            DialogBuyLego(
                visible = buyDialog, carLegos = cart,
                closeDialog = { buyDialog = false },
                buyBtn = {
                    buyDialog = false
                    viewModel.buyLegos(cart)
                }
            )
            LoadingDialog(loading)
        }

    )
}

@Composable
fun FlowLoading(paddingValues: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues = paddingValues),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(modifier = Modifier.size(50.dp))
    }
}

@Composable
fun BodyLego(
    paddingValues: PaddingValues,
    list: List<LegoModel>,
    addCar: (LegoModel) -> Unit = {},
    navigateDetail: (Int) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues = paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 150.dp),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(list) { item ->
                LegoItem(
                    lego = item,
                    setItemCar = { addCar(it) },
                    navigateDetail = { navigateDetail(it.uid) }
                )
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun PreviewBodyLego() {
    BodyLego(paddingValues = PaddingValues(), list = listOf(LegoModel(name = "Item 1")))
}