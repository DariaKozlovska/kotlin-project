package com.example.dsw51762_kotlin.model

import java.util.Date

data class TodoFirebase(
    var id: String = "",
    var title: String = "",
    var content: String = "",
    var createdAt: Date = Date(),
    var isLocked: Boolean = false,
    var password: String? = null
)

fun Todo.toFirebaseTodo(): TodoFirebase {
    return TodoFirebase(
        id = this.firebaseId,
        title = this.title,
        content = this.content,
        createdAt = this.createdAt,
        isLocked = this.isLocked,
        password = this.password
    )
}

fun TodoFirebase.toRoomTodo(): Todo {
    return Todo(
        firebaseId = this.id,
        title = this.title,
        content = this.content,
        createdAt = this.createdAt,
        isLocked = this.isLocked,
        password = this.password
    )
}