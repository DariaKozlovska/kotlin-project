package com.example.dsw51762_kotlin.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.dsw51762_kotlin.utils.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListPage(
    navController: NavController,
    onAddNote: (String, String, String?) -> Unit // dodaliśmy password jako String?
) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLocked by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dodaj notatkę") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = {
                    if (it.length <= 40) title = it
                },
                label = { Text("Nazwa notatki") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text("Treść notatki") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                maxLines = 5
            )

            Row(
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Zabezpiecz notatkę hasłem")
                Checkbox(
                    checked = isLocked,
                    onCheckedChange = { isLocked = it }
                )
            }

            if (isLocked) {
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Hasło") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }

            Button(
                onClick = {
                    if (title.isNotBlank() && content.isNotBlank()) {
                        val pass = if (isLocked && password.isNotBlank()) password else null
                        onAddNote(title, content, pass)
                        title = ""
                        content = ""
                        password = ""
                        isLocked = false
                        navController.navigate(Routes.homePage) {
                            popUpTo(Routes.todoListPage) { inclusive = true }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Dodaj notatkę")
            }
        }
    }
}
