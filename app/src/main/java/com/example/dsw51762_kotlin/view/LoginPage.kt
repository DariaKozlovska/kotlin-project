package com.example.dsw51762_kotlin.view

import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.PromptInfo
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import com.example.dsw51762_kotlin.R
import com.example.dsw51762_kotlin.ui.theme.DarkPurple
import com.example.dsw51762_kotlin.ui.theme.Pink
import com.example.dsw51762_kotlin.utils.Routes
import com.example.testapp.utils.CustomTextField
import kotlinx.coroutines.launch
import java.util.concurrent.Executor

@Composable
fun LoginPage(navController: NavController, onLoginSuccess: () -> Unit) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val activity = context as? FragmentActivity
    val executor: Executor = ContextCompat.getMainExecutor(context)

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var showBiometricPrompt by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "logo",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .padding(top = 70.dp)
                .size(129.dp)
                .align(Alignment.CenterHorizontally)
        )

        Column(verticalArrangement = Arrangement.spacedBy(30.dp)) {
            Text("Sign in", fontSize = 30.sp, fontWeight = FontWeight.Bold, color = DarkPurple)

            CustomTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = "Email or User Name",
                leadingIcon = painterResource(R.drawable.user_icon),
                keyboardType = KeyboardType.Email
            )

            CustomTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = "Password",
                leadingIcon = painterResource(R.drawable.lock_icon),
                trailingIcon = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                visualTransformation = if (passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
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
                    .clickable { /* TODO */ }
            )

            Button(
                onClick = {
                    if (email == "admin" && password == "Admin1") {
                        errorMessage = null
                        navController.navigate(Routes.homePage)
                        onLoginSuccess()
                    } else {
                        coroutineScope.launch {
                            Toast.makeText(context, "Nieprawidłowy login lub hasło", Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Pink),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Sign in", fontSize = 18.sp, fontWeight = FontWeight.W700, color = Color.White)
            }

            Button(
                onClick = { showBiometricPrompt = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = DarkPurple)
            ) {
                Text("Zaloguj się biometrycznie", fontSize = 18.sp, color = Color.White, fontWeight = FontWeight.Bold)
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
                modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
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

    if (showBiometricPrompt && activity != null) {
        val biometricManager = BiometricManager.from(context)
        val canAuth = biometricManager.canAuthenticate(
            BiometricManager.Authenticators.BIOMETRIC_WEAK or BiometricManager.Authenticators.DEVICE_CREDENTIAL
        )

        if (canAuth != BiometricManager.BIOMETRIC_SUCCESS) {
            Toast.makeText(context, "Biometria niedostępna na tym urządzeniu", Toast.LENGTH_SHORT).show()
            showBiometricPrompt = false
        } else {
            val promptInfo = PromptInfo.Builder()
                .setTitle("Uwierzytelnianie biometryczne")
                .setSubtitle("Zaloguj się za pomocą odcisku palca lub twarzy")
                .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_WEAK or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
                .build()

            val biometricPrompt = BiometricPrompt(
                activity,
                executor,
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        navController.navigate(Routes.homePage) {
                            popUpTo(Routes.loginPage) { inclusive = true }
                        }
                        onLoginSuccess()
                    }

                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        super.onAuthenticationError(errorCode, errString)
                        Toast.makeText(context, "Błąd: $errString", Toast.LENGTH_SHORT).show()
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        Toast.makeText(context, "Autoryzacja nie powiodła się", Toast.LENGTH_SHORT).show()
                    }
                }
            )

            LaunchedEffect(Unit) {
                biometricPrompt.authenticate(promptInfo)
                showBiometricPrompt = false
            }
        }
    }
}

