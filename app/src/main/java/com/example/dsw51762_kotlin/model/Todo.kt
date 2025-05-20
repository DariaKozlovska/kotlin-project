package com.example.dsw51762_kotlin.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Todo (
    @PrimaryKey(autoGenerate = true)
    var id: Int=0,
    var title: String,
    val content: String,
    var createdAt: Date,
    var isLocked: Boolean = false,
    var password: String? = null
)