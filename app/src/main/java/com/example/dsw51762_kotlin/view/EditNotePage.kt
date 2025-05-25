package com.example.dsw51762_kotlin.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.dsw51762_kotlin.viewmodel.TodoViewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNotePage(navController: NavController, viewModel: TodoViewModel, noteId: String) {
    val todo by viewModel.getTodoById(noteId).observeAsState()

    var editedTitle by remember(todo) { mutableStateOf(todo?.title ?: "") }
    var editedContent by remember(todo) { mutableStateOf(todo?.content ?: "") }
    var editedPassword by remember(todo) { mutableStateOf(todo?.password ?: "") }
    var isLocked by remember(todo) { mutableStateOf(todo?.isLocked ?: false) }

    LaunchedEffect(todo) {
        todo?.let {
            editedTitle = it.title
            editedContent = it.content
            editedPassword = it.password ?: ""
            isLocked = it.isLocked
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edytuj Notatkę") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (todo != null) {
                        IconButton(onClick = {
                            if (editedTitle.isNotBlank()) {
                                val updatedTodo = todo!!.copy(
                                    title = editedTitle,
                                    content = editedContent,
                                    password = if (isLocked && editedPassword.isNotBlank()) editedPassword else null,
                                    isLocked = isLocked
                                )
                                viewModel.updateTodo(updatedTodo)
                                navController.popBackStack()
                            }
                        }) {
                            Icon(Icons.Default.Done, contentDescription = "Save")
                        }
                    }
                }
            )
        }
    ) { padding ->
        if (todo == null) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = editedTitle,
                    onValueChange = { if (it.length <= 40) editedTitle = it },
                    label = { Text("Tytuł") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                OutlinedTextField(
                    value = editedContent,
                    onValueChange = { editedContent = it },
                    label = { Text("Treść") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    maxLines = Int.MAX_VALUE
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(checked = isLocked, onCheckedChange = { isLocked = it })
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Zablokuj notatkę")
                }
                if (isLocked) {
                    OutlinedTextField(
                        value = editedPassword,
                        onValueChange = { editedPassword = it },
                        label = { Text("Hasło (opcjonalnie)") },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}