package com.example.dsw51762_kotlin.view

import android.content.Context
import android.os.Bundle
import android.util.Log
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
import androidx.navigation.NavType
import androidx.navigation.navArgument

import com.example.dsw51762_kotlin.utils.Routes
import com.example.dsw51762_kotlin.viewmodel.TodoViewModel
import com.example.testapp.views.RegisterPage

import com.example.dsw51762_kotlin.viewmodel.TodoViewModelFactory
import com.example.dsw51762_kotlin.data.FirebaseTodoRepository
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : FragmentActivity() {

    private val firestoreInstance: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    private val firebaseTodoRepository: FirebaseTodoRepository by lazy {
        FirebaseTodoRepository(firestoreInstance)
    }

    private val todoViewModel: TodoViewModel by viewModels {
        TodoViewModelFactory(firebaseTodoRepository)
    }

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
                }
            )
        }
        composable(Routes.todoListPage) {
            TodoListPage(navController) { title, content, password ->
                todoViewModel.addTodo(title, content, password)
            }
        }

        composable("view_note_page/{noteId}",
            arguments = listOf(navArgument("noteId") { type = NavType.StringType })
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId")
            if (noteId != null && noteId.isNotBlank()) {
                ViewNotePage(navController, todoViewModel, noteId)
            } else {
                Log.e("MainActivity", "noteId is null or blank in ViewNotePage: $noteId")
                navController.popBackStack()
            }
        }

        composable("${Routes.editNotePage}/{noteId}",
            arguments = listOf(navArgument("noteId") { type = NavType.StringType })
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId")
            if (noteId != null && noteId.isNotBlank()) {
                EditNotePage(navController, todoViewModel, noteId)
            } else {
                Log.e("MainActivity", "noteId is null or blank in EditNotePage: $noteId")
                navController.popBackStack()
            }
        }
    }
}