package com.storeapp.lego.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.storeapp.lego.R
import com.storeapp.lego.domain.model.ShoppingCarModel

@Composable
fun DialogBuyLego(
    visible: Boolean = false, carLegos: List<ShoppingCarModel>,
    closeDialog: () -> Unit = {},
    buyBtn: () -> Unit = {},
) {
    if (visible) {
        val total by remember { mutableIntStateOf(getTotal(carLegos)) }

        Dialog(
            onDismissRequest = { },
            properties = DialogProperties(dismissOnClickOutside = false)
        ) {
            Column(
                Modifier
                    .padding(10.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(color = Color.White),
            ) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    IconButton(onClick = { closeDialog() }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = null)
                    }
                }
                LazyColumn() {
                    items(carLegos) {
                        ItemCarLego(it)
                    }
                }
                Row(Modifier.fillMaxWidth()) {
                    Text(
                        text = stringResource(R.string.total, total),
                        modifier = Modifier
                            .padding(end = 20.dp)
                            .weight(1f),
                        fontWeight = FontWeight.Bold,
                        fontSize = TextUnit(18f, TextUnitType.Sp),
                        textAlign = TextAlign.End
                    )
                }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = { closeDialog() },
                        modifier = Modifier.width(120.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Gray
                        )
                    ) {
                        Text(text = stringResource(R.string.close))
                    }
                    Button(onClick = { buyBtn() }, modifier = Modifier.width(120.dp)) {
                        Text(text = stringResource(R.string.buy))
                    }
                }
            }
        }
    }
}

fun getTotal(carList: List<ShoppingCarModel>): Int {
    var total = 0
    carList.map {
        total += it.stock * it.unitPrice
    }
    return total
}

@Composable
fun ItemCarLego(carModel: ShoppingCarModel) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = carModel.image,
                contentDescription = null,
                placeholder = painterResource(id = R.drawable.baseline_image_24),
                modifier = Modifier.size(30.dp)
            )

            Column {
                Text(
                    text = carModel.name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.widthIn(max = 160.dp)
                )
                Text(
                    text = stringResource(R.string.value_unit, carModel.unitPrice),
                    fontSize = TextUnit(14f, TextUnitType.Sp)
                )
            }

            Text(
                text = carModel.stock.toString(),
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .weight(1f),
                fontWeight = FontWeight.Bold,
                fontSize = TextUnit(18f, TextUnitType.Sp),
                textAlign = TextAlign.End
            )
        }
    }
}


@Preview
@Composable
fun PreviewDialogBuyLego() {
    DialogBuyLego(
        true, carLegos = listOf(
            ShoppingCarModel(name = "Item 1"),
            ShoppingCarModel(name = "Item 2"),
        )
    )
}
