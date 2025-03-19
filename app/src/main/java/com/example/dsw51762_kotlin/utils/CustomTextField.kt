package com.example.testapp.utils

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dsw51762_kotlin.ui.theme.DarkPurple
import com.example.dsw51762_kotlin.ui.theme.LightPurple

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    leadingIcon: Painter? = null,
    onTrailingIconClick: () -> Unit = {},
    trailingIcon: ImageVector? = null,
    visualTransformation: VisualTransformation? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    cornerRadius: Int = 16
) {

    val visualTransformationEffect = visualTransformation ?: VisualTransformation.None

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = LightPurple, // Border color
                shape = RoundedCornerShape(cornerRadius.dp) // Applying rounded corners
            ),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,

        ),
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder, color = Color.Gray, fontSize = 16.sp) },
        singleLine = true,
        leadingIcon = {
            if (leadingIcon != null)
                Icon(
                    modifier = Modifier.size(30.dp),
                    painter = leadingIcon, contentDescription = null,
                    tint = Color.Unspecified
                )
        },
        trailingIcon = {
            if (trailingIcon != null) {
                IconButton(onClick = onTrailingIconClick) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = trailingIcon, contentDescription = null,
                        tint = DarkPurple
                    )
                }
            }
        },
        visualTransformation = visualTransformation ?: VisualTransformation.None,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType
        )
    )
}