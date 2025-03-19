package com.example.testapp.views

import android.graphics.Point
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.dsw51762_kotlin.R
import com.example.dsw51762_kotlin.ui.theme.DarkPurple
import com.example.dsw51762_kotlin.ui.theme.Pink
import com.example.dsw51762_kotlin.utils.Routes
import com.example.testapp.utils.CustomTextField


@Composable
fun RegisterPage(navController: NavController){

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmationPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(true) }
    var confirmationVisible by remember { mutableStateOf(true) }


    Column (
        modifier = Modifier.fillMaxSize(),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ){
            TextButton(
                onClick = { navController.navigate(Routes.loginPage)},
                modifier = Modifier.padding(top = 70.dp),
            )
            {
                Icon(
                    modifier = Modifier.size(20.dp),
                    imageVector = Icons.Filled.ArrowBackIosNew,
                    contentDescription = null,
                    tint = DarkPurple
                )
                Text(
                    "Back",
                    color = DarkPurple,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W700,
                )
            }
            Image(
                painterResource(R.drawable.corner),
                contentDescription = "Corner image",
                modifier = Modifier.size(width = 150.dp, height = 118.dp),
            )
        }

        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(30.dp),
        )
        {
            Text(
                "Sing up",
                color = DarkPurple,
                fontSize = 30.sp,
                fontWeight = FontWeight.W700,
                modifier = Modifier.padding(top = 50.dp)
            )
            CustomTextField(
                value = name,
                onValueChange = { name = it},
                placeholder = "Full Name",
                leadingIcon = painterResource(R.drawable.user_icon),
                keyboardType = KeyboardType.Text,
            )
            CustomTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = "Email",
                leadingIcon = painterResource(R.drawable.email_icon),
                keyboardType = KeyboardType.Email,
            )
            CustomTextField(
                value = password,
                onValueChange = { password = it },
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
                keyboardType = KeyboardType.Password,
            )
            CustomTextField(
                value = confirmationPassword,
                onValueChange = { confirmationPassword = it },
                placeholder = "Confirm password",
                leadingIcon = painterResource(R.drawable.lock_icon),
                trailingIcon = if (confirmationVisible) {
                    Icons.Filled.Visibility // Eye icon for visible password
                } else {
                    Icons.Filled.VisibilityOff // Eye off icon for hidden password
                },
                visualTransformation = if (confirmationVisible) {
                    PasswordVisualTransformation() // Hide password
                } else {
                    VisualTransformation.None // Show password
                },
                onTrailingIconClick = { confirmationVisible = !confirmationVisible },
                keyboardType = KeyboardType.Password,
            )
            Spacer(
                modifier = Modifier.height(10.dp)
            )
            Button(
                onClick = { navController.navigate(Routes.loginPage)},
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Pink,    // Button background color
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp)
            )
            {
                Text(text = "Sing up", fontSize = 18.sp, fontWeight = FontWeight.W700)
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 64.dp),
                horizontalArrangement = Arrangement.Center,
            ){
                Text(
                    "Already have an account ? ",
                    color = DarkPurple,
                    fontSize = 18.sp,
                )
                Text(
                    "Sing In",
                    color = DarkPurple,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W700,
                    modifier = Modifier.clickable { navController.navigate(Routes.loginPage) }
                )
            }
        }
    }
}
