package com.example.dsw51762_kotlin.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val roomId: Int = 0,
    val firebaseId: String = "",
    val title: String,
    val content: String,
    val createdAt: Date,
    val isLocked: Boolean,
    val password: String?
)