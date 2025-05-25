package com.example.dsw51762_kotlin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dsw51762_kotlin.data.FirebaseTodoRepository // Ważny import do FirebaseTodoRepository

/**
 * Factory for creating [TodoViewModel] with a constructor that takes a [FirebaseTodoRepository].
 * This is necessary because [ViewModel] instances are typically created by the system,
 * and we need to provide a way to pass dependencies.
 */
class TodoViewModelFactory(private val firebaseRepo: FirebaseTodoRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Sprawdza, czy modelClass jest przypisywalny do TodoViewModel
        if (modelClass.isAssignableFrom(TodoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            // Zwraca nową instancję TodoViewModel, przekazując mu nasze repozytorium
            return TodoViewModel(firebaseRepo) as T
        }
        // Jeśli próbujemy stworzyć inny ViewModel, którego ta fabryka nie obsługuje, rzucamy błąd
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}