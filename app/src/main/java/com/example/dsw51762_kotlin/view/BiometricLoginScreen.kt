package com.example.dsw51762_kotlin.view
//
//import android.widget.Toast
//import androidx.activity.ComponentActivity
//import androidx.fragment.app.FragmentActivity
//import androidx.biometric.BiometricManager
//import androidx.biometric.BiometricPrompt
//import androidx.biometric.BiometricPrompt.PromptInfo
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.Text
//import androidx.compose.runtime.*
//import androidx.compose.ui.platform.LocalContext
//import androidx.navigation.NavController
//import androidx.core.content.ContextCompat
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import java.util.concurrent.Executor
//
//@Composable
//fun BiometricLoginScreen(navController: NavController) {
//    val context = LocalContext.current
//    val activity = context as? FragmentActivity
//    val executor: Executor = ContextCompat.getMainExecutor(context)
//    var showPrompt by remember { mutableStateOf(true) }
//
//    if (activity == null) {
//        Toast.makeText(context, "Ten ekran wymaga FragmentActivity", Toast.LENGTH_SHORT).show()
//        return
//    }
//
//    val biometricManager = BiometricManager.from(context)
//    val canAuthenticate = biometricManager.canAuthenticate(
//        BiometricManager.Authenticators.BIOMETRIC_WEAK or BiometricManager.Authenticators.DEVICE_CREDENTIAL
//    )
//
//    if (canAuthenticate != BiometricManager.BIOMETRIC_SUCCESS) {
//        Toast.makeText(context, "Biometria niedostępna", Toast.LENGTH_LONG).show()
//        return
//    }
//
//    if (showPrompt) {
//        val promptInfo: PromptInfo = PromptInfo.Builder()
//            .setTitle("Uwierzytelnianie biometryczne")
//            .setSubtitle("Zaloguj się przy użyciu odcisku palca lub twarzy")
//            .setAllowedAuthenticators(
//                BiometricManager.Authenticators.BIOMETRIC_WEAK or BiometricManager.Authenticators.DEVICE_CREDENTIAL
//            )
//            .build()
//
//        val biometricPrompt = BiometricPrompt(
//            activity,
//            executor,
//            object : BiometricPrompt.AuthenticationCallback() {
//                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
//                    super.onAuthenticationSucceeded(result)
//                    navController.navigate("home_page") {
//                        popUpTo("biometric_login") { inclusive = true }
//                    }
//                }
//
//                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
//                    super.onAuthenticationError(errorCode, errString)
//                    showPrompt = false
//                }
//
//                override fun onAuthenticationFailed() {
//                    super.onAuthenticationFailed()
//                }
//            }
//        )
//
//        LaunchedEffect(Unit) {
//            biometricPrompt.authenticate(promptInfo)
//        }
//    }
//
//    Text("Oczekiwanie na uwierzytelnienie...", modifier = Modifier.padding(16.dp))
//}
