package com.example.bookvault.BD

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import androidx.room.Update

@Dao
interface LivroDAO {
    @Insert
    fun insertLivro(livro: Livro)

    @Query("SELECT * FROM livros WHERE nomeLivro = :name")
    fun findLivroByName(name: String): List<Livro>

    @Query("DELETE FROM livros WHERE nomeLivro = :name")
    fun deleteLivroByName(name: String)

    @Query("SELECT * FROM livros")
    fun getAllLivros(): LiveData<List<Livro>>

    @Update
    fun updateLivro(livro: Livro)

    @Delete
    fun deleteLivro(livro: Livro)

    @Query("DELETE FROM livros")
    fun deleteAllBooks()
}