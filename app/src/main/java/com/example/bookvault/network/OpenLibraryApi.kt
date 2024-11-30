package com.example.bookvault.network

import retrofit2.http.GET
import retrofit2.http.Query

// Data classes to map the Open Library API response
data class BookResponse(
    val docs: List<Book>
)

data class Book(
    val title: String?,
    val author_name: List<String>?,
    val first_publish_year: Int?,
    val cover_i: Int?,
    val key: String // This is a unique identifier for each book
)

interface OpenLibraryApiService {
    @GET("search.json")
    suspend fun searchBooks(@Query("q") query: String): BookResponse
}