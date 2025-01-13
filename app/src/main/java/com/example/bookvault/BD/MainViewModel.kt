package com.example.bookvault.BD

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val allLivros: LiveData<List<Livro>>
    private val repository: LivroRepository
    val searchResults: MutableLiveData<List<Livro>>

    init {
        // Initialize the database and DAO
        val livroDb = LivroRoomDatabase.getInstance(application)
        val livroDao = livroDb.livroDAO()

        // Initialize the repository
        repository = LivroRepository(livroDao)

        // Observe all books and search results
        allLivros = repository.allLivros
        searchResults = repository.searchResults
    }

    // Insert a new book
    fun insertLivro(livro: Livro) {
        repository.insertLivro(livro)
    }

    // Find a book by name
    fun findLivroByName(nome: String) {
        repository.findLivroByName(nome)
    }

    // Delete a book by name
    fun deleteLivroByName(nome: String) {
        repository.deleteLivroByName(nome)
    }

    // Update a book
    fun updateLivro(livro: Livro) {
        repository.updateLivro(livro)
    }

    // Delete a specific book
    fun deleteLivro(livro: Livro) {
        repository.deleteLivro(livro)
    }
    fun deleteAllBooks() {
        repository.deleteAllBooks()
    }
}