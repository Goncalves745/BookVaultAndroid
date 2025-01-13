package com.example.bookvault.BD

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*

class LivroRepository(private val livroDAO: LivroDAO) {

    // LiveData to observe all books
    val allLivros: LiveData<List<Livro>> = livroDAO.getAllLivros()

    // MutableLiveData to hold search results
    val searchResults = MutableLiveData<List<Livro>>()

    // Coroutine scope for async operations
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    // Insert a new book
    fun insertLivro(newLivro: Livro) {
        coroutineScope.launch(Dispatchers.IO) {
            livroDAO.insertLivro(newLivro)
        }
    }

    // Delete a book by name
    fun deleteLivroByName(name: String) {
        coroutineScope.launch(Dispatchers.IO) {
            livroDAO.deleteLivroByName(name)
        }
    }

    // Search for a book by name
    fun findLivroByName(name: String) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = asyncFindLivro(name).await()
        }
    }

    // Async helper function for searching a book
    private fun asyncFindLivro(name: String): Deferred<List<Livro>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async livroDAO.findLivroByName(name)
        }

    // Update a book's details
    fun updateLivro(updatedLivro: Livro) {
        coroutineScope.launch(Dispatchers.IO) {
            livroDAO.updateLivro(updatedLivro)
        }
    }

    // Delete a specific book
    fun deleteLivro(livro: Livro) {
        coroutineScope.launch(Dispatchers.IO) {
            livroDAO.deleteLivro(livro)
        }
    }
    fun deleteAllBooks() {
        coroutineScope.launch(Dispatchers.IO) {
            livroDAO.deleteAllBooks()
        }
    }
}