@file:OptIn(ExperimentalMaterial3Api::class)

package com.storeapp.lego.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.storeapp.lego.R
import com.storeapp.lego.domain.model.DetailLegoModel
import com.storeapp.lego.domain.model.LegoModel
import com.storeapp.lego.ui.component.DialogBuyLego
import com.storeapp.lego.ui.component.LoadingDialog
import com.storeapp.lego.ui.component.TopBarShoppingCar
import com.storeapp.lego.ui.screens.viewmodels.DetLegoViewModel
import com.storeapp.lego.utils.ResState

@Composable
fun DetailLegoScreen(
    viewModel: DetLegoViewModel = hiltViewModel(),
    navController: NavHostController,
    legoId: String
) {

    val buyLegos by viewModel.buyState.observeAsState(ResState.Success(false))
    val loading by viewModel.loading.observeAsState(initial = false)
    val detail by viewModel.infoDetail.observeAsState()

    val lego by viewModel.lego(legoId).collectAsState(initial = null)
    val cart by viewModel.cart.collectAsState(initial = emptyList())

    var buyDialog by remember { mutableStateOf(false) }
    val snackBarState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            TopBarShoppingCar(
                itemsCart = cart,
                displayCartDialog = { buyDialog = true },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "det_back"
                        )
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackBarState) }
    ) { paddingValues ->

        when (lego) {
            null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues = paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_error_outline_24),
                        contentDescription = null
                    )
                }
            }

            else -> {
                if (detail is ResState.Error) {
                    LaunchedEffect(key1 = true) {
                        val err = detail as ResState.Error
                        snackBarState.showSnackbar(
                            message = "${err.message} - ${err.cause}",
                            duration = SnackbarDuration.Short
                        )
                    }
                }

                BodyDet(paddingValues, lego!!, detail) {
                    viewModel.addCart(it)
                }

                when (buyLegos) {
                    is ResState.Error -> {
                        val err = (buyLegos as ResState.Error)
                        LaunchedEffect(key1 = true) {
                            snackBarState.showSnackbar(
                                message = "${err.message} - ${err.cause}",
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                    is ResState.Success -> {
                        val stateBuy = (buyLegos as ResState.Success<Boolean>).data
                        if (stateBuy) navController.popBackStack()
                    }
                }
            }
        }

        LaunchedEffect(true) { viewModel.getDetailLego(legoId) }

        DialogBuyLego(
            visible = buyDialog, carLegos = cart,
            closeDialog = { buyDialog = false },
            buyBtn = {
                buyDialog = false
                viewModel.buyLegos(cart)
            }
        )

        LoadingDialog(showLoading = loading)
    }
}

@Composable
fun BodyDet(
    paddingValues: PaddingValues,
    lego: LegoModel,
    description: ResState<DetailLegoModel>?,
    setItemCar: (LegoModel) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(contentAlignment = Alignment.TopEnd) {
            AsyncImage(
                model = lego.image,
                contentDescription = "det_lego",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.FillHeight
            )

            IconButton(onClick = { setItemCar(lego) }) {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = "add_cart",
                    modifier = Modifier.size(30.dp),
                    tint = if (lego.stock > 0) LocalContentColor.current else Color.LightGray
                )
            }
        }

        Column(
            Modifier
                .fillMaxSize()
                .padding(all = 20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            Text(text = lego.name, fontWeight = FontWeight.Bold)

            Row(
                Modifier.padding(bottom = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = stringResource(R.string.count, lego.stock),
                    fontWeight = FontWeight.Medium
                )

                Text(
                    text = stringResource(R.string.price, lego.unitPrice),
                    fontWeight = FontWeight.Medium
                )
            }

            if (description is ResState.Success) {
                Text(text = description.data.description)
            }

        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewDetailLegoScreen() {
    BodyDet(PaddingValues(), LegoModel(), null)
}