@file:OptIn(ExperimentalMaterial3Api::class)

package com.storeapp.lego.ui.component

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType


@Composable
fun TextFieldIcons(
    value: String = "",
    onChangeText: (String) -> Unit = {},
    placeholder: String = "",
    modifier: Modifier = Modifier,
    fontSize: Float = 16f,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    leadingIcon: @Composable() (() -> Unit)? = null,
    trailingIcon: @Composable() (() -> Unit)? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = { onChangeText(it) },
        placeholder = {
            Text(
                text = placeholder,
                fontSize = TextUnit(fontSize, TextUnitType.Sp)
            )
        },
        modifier = modifier,
        shape = ShapeDefaults.Medium,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        singleLine = singleLine,
        keyboardOptions = keyboardOptions,
        textStyle = TextStyle(color = MaterialTheme.colorScheme.primary),
        visualTransformation = visualTransformation,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            containerColor = Color.Transparent
        )
    )
}