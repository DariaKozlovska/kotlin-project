package com.example.dsw51762_kotlin.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.dsw51762_kotlin.viewmodel.TodoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewNotePage(navController: NavController, viewModel: TodoViewModel, noteId: Int) {
    val todo by viewModel.getTodoById(noteId).observeAsState()
    var hasAccess by remember { mutableStateOf(false) }
    var enteredPassword by remember { mutableStateOf("") }
    var showPasswordDialog by remember { mutableStateOf(false) }
    var showPasswordError by remember { mutableStateOf(false) }

    LaunchedEffect(todo?.id) {
        todo?.let {
            if (it.isLocked) {
                showPasswordDialog = true
            } else {
                hasAccess = true
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Podgląd notatki") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate("edit_note_page/${noteId}")
                    }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit")
                    }
                }
            )
        }
    ) { padding ->
        if (todo == null) {
            Text("Ładowanie...", modifier = Modifier.padding(16.dp))
        } else if (hasAccess) {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
            ) {
                Text(text = "Tytuł:", style = MaterialTheme.typography.titleMedium)
                Text(text = todo!!.title, style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Treść:", style = MaterialTheme.typography.titleMedium)
                Text(text = todo!!.content, style = MaterialTheme.typography.bodyLarge)
            }
        }

        // Dialog z hasłem
        if (showPasswordDialog) {
            AlertDialog(
                onDismissRequest = {
                    showPasswordDialog = false
                    navController.popBackStack()
                },
                title = { Text("Podaj hasło") },
                text = {
                    Column {
                        OutlinedTextField(
                            value = enteredPassword,
                            onValueChange = {
                                enteredPassword = it
                                showPasswordError = false
                            },
                            label = { Text("Hasło") },
                            singleLine = true
                        )
                        if (showPasswordError) {
                            Text(
                                text = "Nieprawidłowe hasło",
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        if (enteredPassword == todo?.password) {
                            hasAccess = true
                            showPasswordDialog = false
                        } else {
                            showPasswordError = true
                        }
                    }) {
                        Text("Zatwierdź")
                    }
                },
                dismissButton = {
                    Button(onClick = {
                        showPasswordDialog = false
                        navController.popBackStack()
                    }) {
                        Text("Anuluj")
                    }
                }
            )
        }
    }
}
