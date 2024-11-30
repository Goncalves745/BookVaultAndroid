package com.example.bookvault.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookvault.network.Book
import com.example.bookvault.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BookViewModel : ViewModel() {
    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books: StateFlow<List<Book>> get() = _books

    fun searchBooks(query: String) {
        if (query.isNotBlank()) {
            viewModelScope.launch {
                try {
                    val response = RetrofitInstance.retrofitService.searchBooks(query)
                    _books.value = response.docs
                } catch (e: Exception) {
                    e.printStackTrace()
                    _books.value = emptyList() // Handle errors gracefully
                }
            }
        } else {
            _books.value = emptyList() // Clear results when query is empty
        }
    }
}