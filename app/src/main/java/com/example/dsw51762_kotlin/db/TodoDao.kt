package com.example.dsw51762_kotlin.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.dsw51762_kotlin.model.Todo

@Dao
interface TodoDao{
    @Query("SELECT * FROM Todo ORDER BY createdAt DESC")
    fun getAllTodo() : LiveData<List<Todo>>

    @Insert
    fun addTodo(todo: Todo)

    @Query("SELECT * FROM todo WHERE firebaseId = :id LIMIT 1")
    fun getTodoById(id: Int): LiveData<Todo>

    @Update
    suspend fun updateTodo(todo: Todo)

    @Query("DELETE FROM Todo WHERE firebaseId = :id")
    fun deleteTodo(id : Int)
}