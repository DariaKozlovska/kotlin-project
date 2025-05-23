package com.example.dsw51762_kotlin.view

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dsw51762_kotlin.utils.Routes
import com.example.dsw51762_kotlin.viewmodel.TodoViewModel
import com.example.testapp.views.RegisterPage
import androidx.navigation.NavType
import androidx.navigation.navArgument


class MainActivity : FragmentActivity() {

    private val todoViewModel: TodoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val sharedPrefs = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPrefs.getBoolean("is_logged_in", false)

        setContent {
            Surface(color = MaterialTheme.colorScheme.background) {
                val navController = rememberNavController()
                SetupNavGraph(navController, todoViewModel, isLoggedIn, sharedPrefs)
            }
        }
    }
}

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    todoViewModel: TodoViewModel,
    isLoggedIn: Boolean,
    sharedPrefs: android.content.SharedPreferences
) {
    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) Routes.homePage else Routes.loginPage
    ) {
        composable(Routes.loginPage) {
            LoginPage(navController) {
                sharedPrefs.edit().putBoolean("is_logged_in", true).apply()
                navController.navigate(Routes.homePage) {
                    popUpTo(Routes.loginPage) { inclusive = true }
                }
            }
        }
        composable(Routes.registerPage) {
            RegisterPage(navController)
        }
        composable(Routes.homePage) {
            HomePage(
                navController,
                todoViewModel,
                onLogout = {
                    sharedPrefs.edit().putBoolean("is_logged_in", false).apply()
                    navController.navigate(Routes.loginPage) {
                        popUpTo(Routes.homePage) { inclusive = true }
                    }
                },
                onNoteClick = { id ->
                    navController.navigate("${Routes.viewNotePage}/$id")
                }
            )
        }
        composable(Routes.todoListPage) {
            TodoListPage(navController) { title, content, password ->
                todoViewModel.addTodo(title, content, password)
            }
        }

//        composable(Routes.biometricLoginScreen) {
//            BiometricLoginScreen(navController)
//        }


        composable("view_note_page/{noteId}",
            arguments = listOf(navArgument("noteId") { type = NavType.IntType })
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getInt("noteId") ?: -1
            ViewNotePage(navController, todoViewModel, noteId)
        }

        composable("${Routes.editNotePage}/{noteId}") { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId")?.toIntOrNull()
            if (noteId != null) {
                EditNotePage(navController, todoViewModel, noteId)
            }
        }
    }
}
