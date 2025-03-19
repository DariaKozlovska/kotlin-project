package com.example.dsw51762_kotlin.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.Color
import androidx.navigation.Navigation
import com.example.dsw51762_kotlin.ui.theme.LightPurple
import com.example.dsw51762_kotlin.ui.theme.Pink


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

            Text(
                "Forget Password?",
                fontSize = 18.sp,
                fontWeight = FontWeight.W700,
                color = DarkPurple,
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable {
                    // TODO: navigation to one of the pages
                }
            )

            Button(
                onClick = { navController.navigate(Routes.registerPage)},
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Pink,    // Button background color
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp)
            )
            {
                Text(text = "Sing in", fontSize = 18.sp, fontWeight = FontWeight.W700)
            }

            Text(
                "Or sign in With",
                fontSize = 18.sp,
                color = DarkPurple,
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 20.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 50.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ){
                ElevatedButton(
                    onClick = {
//                    TODO: Idk if its needed
                    },
                    modifier = Modifier.height(50.dp).width(50.dp),
                    shape = RoundedCornerShape(16.dp),
                    contentPadding = PaddingValues(10.dp),
                ) {
                    Image(
                        painterResource(R.drawable.google_icon),
                        contentDescription = "Google icon",
                        modifier = Modifier.size(40.dp),
                    )
                }
                ElevatedButton(
                    onClick = {
//                    TODO: Idk if its needed
                    },
                    modifier = Modifier.height(50.dp).width(50.dp),
                    shape = RoundedCornerShape(16.dp),
                    contentPadding = PaddingValues(10.dp),
                ) {
                    Image(
                        painterResource(R.drawable.fb_icon),
                        contentDescription = "FB icon",
                        modifier = Modifier.size(40.dp),
                    )
                }
                ElevatedButton(
                    onClick = {
//                    TODO: Idk if its needed
                    },
                    modifier = Modifier.height(50.dp).width(50.dp),
                    shape = RoundedCornerShape(16.dp),
                    contentPadding = PaddingValues(10.dp),
                ) {
                    Image(
                        painterResource(R.drawable.x_icon),
                        contentDescription = "X icon",
                        modifier = Modifier.size(40.dp),
                    )
                }
                ElevatedButton(
                    onClick = {
//                    TODO: Idk if its needed
                    },
                    modifier = Modifier.height(50.dp).width(50.dp),
                    shape = RoundedCornerShape(16.dp),
                    contentPadding = PaddingValues(10.dp),
                ) {
                    Image(
                        painterResource(R.drawable.in_icon),
                        contentDescription = "In icon",
                        modifier = Modifier.size(40.dp),
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 30.dp),
                horizontalArrangement = Arrangement.Center,
            )
            {
                Text(
                    "Don't have an account? ",
                    modifier = Modifier,
                    color = DarkPurple,
                    fontSize = 18.sp
                )
                Text(
                    "Sing up",
                    modifier = Modifier.clickable { navController.navigate(Routes.registerPage) },
                    color = DarkPurple,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W700
                )
            }
        }
    }
}