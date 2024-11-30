package com.example.bookvault.repository

import com.example.bookvault.network.RetrofitInstance

class BookRepository {
    private val api = RetrofitInstance.retrofitService  // Change 'api' to 'retrofitService'

    suspend fun searchBooks(query: String) = api.searchBooks(query)
}