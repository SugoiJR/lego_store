@file:OptIn(ExperimentalMaterial3Api::class)

package com.storeapp.lego.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.storeapp.lego.R
import com.storeapp.lego.domain.model.ShoppingCarModel

@Composable
fun TopBarShoppingCar(
    title: String = stringResource(R.string.app_name),
    itemsCart: List<ShoppingCarModel>,
    displayCartDialog: () -> Unit = {},
    navigationIcon: @Composable () -> Unit = {},
    options: @Composable () -> Unit = {}
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = navigationIcon,
        actions = {
            IconButton(onClick = { if (itemsCart.isNotEmpty()) displayCartDialog() }) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "shopping_cart"
                )
                if (itemsCart.isNotEmpty()) {
                    var count = 0
                    itemsCart.forEach { cart ->
                        count += cart.stock
                    }
                    Badge(count = count)
                }
            }

            options()
        }
    )
}


@Composable
fun Badge(count: Int) {
    Box(
        modifier = Modifier
            .size(18.dp)
            .offset(x = 14.dp, y = (-10).dp)
            .background(Color.Red, shape = MaterialTheme.shapes.large),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = count.toString(),
            fontWeight = FontWeight.Medium,
            color = Color.White,
            fontSize = 10.sp
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewTopBarCar() {
    TopBarShoppingCar(itemsCart = emptyList())
}