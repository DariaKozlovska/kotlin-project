package com.example.dsw51762_kotlin.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
// Nie potrzebujesz już androidx.lifecycle.map, jeśli mapujesz Flow,
// ale możesz go zostawić, jeśli używasz go w innych miejscach z LiveData.
import androidx.lifecycle.asLiveData // Importuj to do konwersji Flow na LiveData

import com.example.dsw51762_kotlin.model.Todo
import com.example.dsw51762_kotlin.model.TodoFirebase
import com.example.dsw51762_kotlin.model.toFirebaseTodo
import com.example.dsw51762_kotlin.model.toRoomTodo
import com.example.dsw51762_kotlin.data.FirebaseTodoRepository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.Date

// WAŻNY IMPORT: Operator map dla Flow
import kotlinx.coroutines.flow.map // To jest import, którego brakowało!

class TodoViewModel(private val firebaseRepo: FirebaseTodoRepository) : ViewModel() {

    // --- Sekcja dotycząca Room (jeśli jej nie używasz, możesz te linie usunąć) ---
    // Jeśli nadal używasz Room do innych celów, możesz zostawić te odwołania.
    // Pamiętaj jednak, że ten ViewModel będzie operował głównie na Firebase.
    // val todoDao = MainApplication.todoDatabase.getTodoDao()
    // val todoListRoom : LiveData<List<Todo>> = todoDao.getAllTodo()
    // --- Koniec sekcji Room ---

    // Główna lista notatek, która będzie pobierana w czasie rzeczywistym z Firebase.
    // 1. firebaseRepo.getAllTodosRealtime() zwraca Flow<List<TodoFirebase>>
    // 2. .map { ... } - ten operator map pochodzi z kotlinx.coroutines.flow
    //    i przekształca każdą emitowaną listę TodoFirebase na listę Todo.
    // 3. .asLiveData(Dispatchers.IO) - konwertuje wynikowy Flow<List<Todo>> na LiveData<List<Todo>>.
    val todoList: LiveData<List<Todo>> = firebaseRepo.getAllTodosRealtime()
        .map { firebaseTodos -> // 'firebaseTodos' jest typu List<TodoFirebase>
            firebaseTodos.map { it.toRoomTodo() } // 'it' w tej wewnętrznej lambdzie jest typu TodoFirebase
        }
        .asLiveData(Dispatchers.IO) // Konwertujemy Flow na LiveData na wątku IO

    // Dodawanie notatki do Firebase Firestore.
    fun addTodo(title: String, content: String, password: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            val newTodo = Todo(
                title = title,
                content = content,
                createdAt = Date.from(Instant.now()),
                isLocked = !password.isNullOrBlank(),
                password = password
            )
            val firebaseTodo = newTodo.toFirebaseTodo()
            val firebaseId = firebaseRepo.addTodo(firebaseTodo)
            // Tutaj możesz zaktualizować lokalną listę z firebaseId jeśli potrzebujesz
        }
    }

    // Usuwanie notatki z Firebase Firestore na podstawie jej ID (String).
    fun deleteTodo(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // 1. Usuń z Firebase
                val firebaseSuccess = firebaseRepo.deleteTodo(id)

                if (firebaseSuccess) {
                    Log.d("DELETE", "Successfully deleted from Firebase: $id")

                    // 2. Opcjonalnie: usuń z lokalnej bazy Room (jeśli używasz)
                    // todoDao.deleteByFirebaseId(id)
                } else {
                    Log.e("DELETE", "Failed to delete from Firebase: $id")
                }
            } catch (e: Exception) {
                Log.e("DELETE", "Error during deletion", e)
            }
        }
    }

    // Aktualizuje istniejącą notatkę w Firebase Firestore.
    fun updateTodo(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            firebaseRepo.updateTodo(todo.toFirebaseTodo())
        }
    }

    // Pobiera pojedynczą notatkę z Firebase Firestore na podstawie jej ID (String).
    fun getTodoById(noteId: String): LiveData<Todo?> {
        val liveData = MutableLiveData<Todo?>()
        viewModelScope.launch(Dispatchers.IO) {
            val firebaseTodo = firebaseRepo.getTodoById(noteId)
            liveData.postValue(firebaseTodo?.toRoomTodo())
        }
        return liveData
    }
}