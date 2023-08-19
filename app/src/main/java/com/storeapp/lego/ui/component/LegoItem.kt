package com.storeapp.lego.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.storeapp.lego.R
import com.storeapp.lego.domain.model.LegoModel


@Composable
fun LegoItem(
    lego: LegoModel,
    setItemCar: (LegoModel) -> Unit = {},
    navigateDetail: (LegoModel) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .wrapContentSize(align = Alignment.Center)
            .background(color = Color.White)
            .clickable { navigateDetail(lego) },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.elevatedCardElevation(4.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(6.dp)
        ) {

            Box(contentAlignment = Alignment.TopEnd) {
                AsyncImage(
                    model = lego.image,
                    placeholder = painterResource(R.drawable.baseline_image_24),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
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

            Text(
                text = lego.name,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLegoItem() {
    LegoItem(lego = LegoModel(name = "Lego item"))
}