package com.example.dsw51762_kotlin.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.dsw51762_kotlin.viewmodel.TodoViewModel
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewNotePage(navController: NavController, viewModel: TodoViewModel, noteId: String) {
    val todo by viewModel.getTodoById(noteId).observeAsState()
    var hasAccess by remember { mutableStateOf(false) }
    var enteredPassword by remember { mutableStateOf("") }
    var showPasswordDialog by remember { mutableStateOf(false) }
    var showPasswordError by remember { mutableStateOf(false) }

    LaunchedEffect(todo, noteId) {
        if (todo == null) {
            hasAccess = false
            showPasswordDialog = false
        } else {
            if (todo!!.isLocked) {
                if (!hasAccess) {
                    showPasswordDialog = true
                }
            } else {
                hasAccess = true
                showPasswordDialog = false
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                        Text(
                            text = "Podgląd notatki",
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                },
                actions = {
                    if (hasAccess && todo != null) {
                        IconButton(onClick = {
                            navController.navigate("edit_note_page/${noteId}")
                        }) {
                            Icon(
                                Icons.Default.Edit,
                                contentDescription = "Edit",
                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                }
            )
        }
    ) { padding ->
        if (todo == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (hasAccess) {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                Text(
                    text = SimpleDateFormat("HH:mm dd.MM.yyyy", Locale.getDefault())
                        .format(todo!!.createdAt),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = todo!!.title,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = todo!!.content,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            }
        }

        if (showPasswordDialog && todo != null && todo!!.isLocked) {
            AlertDialog(
                onDismissRequest = {
                    showPasswordDialog = false
                    navController.popBackStack()
                },
                title = { Text("Notatka zablokowana") },
                text = {
                    Column {
                        OutlinedTextField(
                            value = enteredPassword,
                            onValueChange = {
                                enteredPassword = it
                                showPasswordError = false
                            },
                            label = { Text("Podaj hasło") },
                            singleLine = true,
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            modifier = Modifier.fillMaxWidth(),
                            isError = showPasswordError
                        )
                        if (showPasswordError) {
                            Text(
                                text = "Nieprawidłowe hasło",
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        if (enteredPassword == todo?.password) {
                            hasAccess = true
                            showPasswordDialog = false
                            showPasswordError = false
                        } else {
                            showPasswordError = true
                        }
                    }) {
                        Text("Zatwierdź")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
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