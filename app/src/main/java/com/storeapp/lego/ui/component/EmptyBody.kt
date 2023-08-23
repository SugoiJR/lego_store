package com.storeapp.lego.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import com.storeapp.lego.R

@Composable
fun EmptyBody(
    paddingValues: PaddingValues,
    message: String = stringResource(R.string.empty_stock)
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues = paddingValues),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message, fontSize = TextUnit(20f, TextUnitType.Sp)
        )
    }
}