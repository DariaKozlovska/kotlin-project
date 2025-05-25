package com.example.dsw51762_kotlin.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.dsw51762_kotlin.model.Todo
import com.example.dsw51762_kotlin.ui.theme.DarkPurple
import com.example.dsw51762_kotlin.ui.theme.Pink
import com.example.dsw51762_kotlin.utils.Routes
import com.example.dsw51762_kotlin.viewmodel.TodoViewModel
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.ui.graphics.vector.ImageVector

enum class NoteFilter {
    ALL, UNLOCKED, LOCKED
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(
    navController: NavController,
    viewModel: TodoViewModel,
    onLogout: () -> Unit,
//    onNoteClick: (Any?) -> Unit,
) {
    val todoList by viewModel.todoList.observeAsState(emptyList())
    var selectedFilter by remember { mutableStateOf(NoteFilter.ALL) }

    val filteredList = remember(todoList, selectedFilter) {
        when (selectedFilter) {
            NoteFilter.ALL -> todoList
            NoteFilter.UNLOCKED -> todoList.filter { !it.isLocked }
            NoteFilter.LOCKED -> todoList.filter { it.isLocked }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Notatki") },
                navigationIcon = {
                    IconButton(onClick = {
                        onLogout()
                    }) {
                        Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = "Logout")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Routes.todoListPage)
                    }) {
                        Icon(Icons.Default.Add, contentDescription = "Dodaj")
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    FilterIcon(
                        icon = Icons.AutoMirrored.Filled.List,
                        contentDescription = "Wszystkie",
                        selected = selectedFilter == NoteFilter.ALL
                    ) { selectedFilter = NoteFilter.ALL }

                    FilterIcon(
                        icon = Icons.Default.Lock,
                        contentDescription = "Zablokowane",
                        selected = selectedFilter == NoteFilter.LOCKED
                    ) { selectedFilter = NoteFilter.LOCKED }

                    FilterIcon(
                        icon = Icons.Default.LockOpen,
                        contentDescription = "Odblokowane",
                        selected = selectedFilter == NoteFilter.UNLOCKED
                    ) { selectedFilter = NoteFilter.UNLOCKED }
                }
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()) {
            if (filteredList.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Brak notatek w tej kategorii", fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { navController.navigate(Routes.todoListPage) }) {
                        Text("Dodaj notatkę")
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(8.dp)
                ) {
                    itemsIndexed(filteredList) { _, item ->
                        TodoItem(item = item, onDelete = {
                            if (item.firebaseId.toString().isNotBlank()) {
                                viewModel.deleteTodo(item.firebaseId.toString())
                                Log.d("DELETE", "Deleting item with ID: ${item.firebaseId}")
                            } else {
                                Log.e("HomePage", "Cannot delete - invalid ID")
                            }
                        }, onClick = {
                            navController.navigate("view_note_page/${item.firebaseId.toString()}")
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun FilterIcon(
    icon: ImageVector,
    contentDescription: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = if (selected) MaterialTheme.colorScheme.primary else Color.Gray
        )
    }
}

@Composable
fun TodoItem(item: Todo, onDelete: () -> Unit, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(if (item.isLocked) MaterialTheme.colorScheme.primary else Pink)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = SimpleDateFormat("HH:mm:ss, dd/MM/yyyy", Locale.ENGLISH).format(item.createdAt),
                fontSize = 12.sp,
                color = Color.LightGray
            )
            Text(
                text = item.title,
                fontSize = 20.sp,
                color = Color.White
            )
        }

        if (item.isLocked) {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = "Zablokowana notatka",
                tint = DarkPurple,
                modifier = Modifier.size(20.dp)
            )
        }

        IconButton(onClick = onDelete) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Usuń",
                tint = DarkPurple,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}