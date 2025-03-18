package com.example.dsw51762_kotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dsw51762_kotlin.utils.Routes
import com.example.dsw51762_kotlin.view.LoginPage
import com.example.testapp.views.RegisterPage


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = Routes.loginPage, builder = {
                composable(Routes.loginPage){
                    LoginPage(navController)
                }
                composable(Routes.registerPage){
                    RegisterPage()
                }
            })
        }
    }
}
