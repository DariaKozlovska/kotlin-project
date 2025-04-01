package com.example.dsw51762_kotlin

import android.app.Application
import androidx.room.Room
import com.example.dsw51762_kotlin.db.TodoDatabase

class MainApplication : Application() {

    companion object{
        lateinit var todoDatabase: TodoDatabase
    }

    override fun onCreate() {
        super.onCreate()
        todoDatabase = Room.databaseBuilder(
            applicationContext,
            TodoDatabase::class.java,
            TodoDatabase.Name
        ).build()
    }
}