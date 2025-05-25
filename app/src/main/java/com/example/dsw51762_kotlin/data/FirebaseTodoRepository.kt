package com.example.dsw51762_kotlin.data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import android.util.Log
import com.example.dsw51762_kotlin.model.TodoFirebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.channels.awaitClose

class FirebaseTodoRepository(private val firestore: FirebaseFirestore) {

    private val todosCollection = firestore.collection("todos")

    init {
        Log.d("FirebaseRepo", "Firestore instance: ${firestore.app.name}")
    }

    suspend fun addTodo(todoFirebase: TodoFirebase): String? {
        return try {
            val docRef = todosCollection.add(todoFirebase).await()
            val newId = docRef.id
            todosCollection.document(newId).update("id", newId).await()
            Log.d("FirebaseRepo", "Todo added with ID: $newId")
            newId
        } catch (e: Exception) {
            Log.e("FirebaseRepo", "Error adding todo", e)
            null
        }
    }

    suspend fun updateTodo(todoFirebase: TodoFirebase): Boolean {
        return try {
            if (todoFirebase.id.isBlank()) {
                Log.e("FirebaseRepo", "Cannot update todo: ID is blank")
                return false
            }
            todosCollection.document(todoFirebase.id).set(todoFirebase).await()
            Log.d("FirebaseRepo", "Todo updated: ${todoFirebase.id}")
            true
        } catch (e: Exception) {
            Log.e("FirebaseRepo", "Error updating todo", e)
            false
        }
    }

    suspend fun deleteTodo(todoId: String): Boolean {
        if (todoId.isBlank()) {
            Log.e("FirebaseRepo", "Cannot delete: ID is blank")
            return false
        }
        return try {
            todosCollection.document(todoId).delete().await()
            Log.d("FirebaseRepo", "Todo deleted: $todoId")
            true
        } catch (e: Exception) {
            Log.e("FirebaseRepo", "Error deleting todo ID: $todoId", e)
            false
        }
    }

    fun getAllTodosRealtime(): Flow<List<TodoFirebase>> = callbackFlow {
        val subscription = todosCollection
            .orderBy("createdAt", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.w("FirebaseRepo", "Listen failed.", e)
                    close(e)
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val todos = snapshot.documents.mapNotNull { document ->
                        val todoFirebase = document.toObject(TodoFirebase::class.java)
                        todoFirebase?.id = document.id
                        todoFirebase
                    }
                    trySend(todos)
                } else {
                    trySend(emptyList())
                }
            }
        awaitClose { subscription.remove() }
    }

    suspend fun getTodoById(id: String): TodoFirebase? {
        return try {
            val document = todosCollection.document(id).get().await()
            val todoFirebase = document.toObject(TodoFirebase::class.java)
            todoFirebase?.id = document.id
            todoFirebase
        } catch (e: Exception) {
            Log.e("FirebaseRepo", "Error getting todo by ID: $id", e)
            null
        }
    }
}