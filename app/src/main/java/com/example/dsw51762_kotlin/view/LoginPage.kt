package com.example.dsw51762_kotlin.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.dsw51762_kotlin.R
import com.example.dsw51762_kotlin.ui.theme.DarkPurple
import com.example.dsw51762_kotlin.utils.Routes
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.testapp.utils.CustomTextField
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff


@Composable
fun LoginPage(navController: NavController){

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var text by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(true) }

    Column (
        modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    )
    {
        Image(
            painterResource(R.drawable.logo),
            contentDescription = "logo",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .padding(top = 70.dp)
                .size(129.dp)
                .align(Alignment.CenterHorizontally)
        )
        Column (
            verticalArrangement = Arrangement.spacedBy(30.dp)
        )
        {
            Text("Sing in",
                fontSize = 30.sp,
                fontWeight = FontWeight.W700,
                color = DarkPurple,
            )

            CustomTextField(
                value = email,
                onValueChange = {email = it},
                modifier = Modifier,
                placeholder = "Email or User Name",
                leadingIcon = painterResource(R.drawable.user_icon),
                keyboardType = KeyboardType.Email
            )

            CustomTextField(
                value = password,
                onValueChange = {password = it},
                modifier = Modifier,
                placeholder = "Password",
                leadingIcon = painterResource(R.drawable.lock_icon),
                trailingIcon = if (passwordVisible) {
                    Icons.Filled.Visibility // Eye icon for visible password
                } else {
                    Icons.Filled.VisibilityOff // Eye off icon for hidden password
                },
                visualTransformation = if (passwordVisible) {
                    PasswordVisualTransformation() // Hide password
                } else {
                    VisualTransformation.None // Show password
                },
                onTrailingIconClick = { passwordVisible = !passwordVisible },
                keyboardType = KeyboardType.Password
            )
            Button(onClick = { navController.navigate(Routes.registerPage)}) {
                Text(text = "Sing in")
            }
        }

    }
}