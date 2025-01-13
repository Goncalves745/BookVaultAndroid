package com.example.bookvault.BD

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "livros")
data class Livro(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "nomeLivro")
    val nomeLivro: String,
    @ColumnInfo(name = "autor")
    val autor: String?,
    @ColumnInfo(name = "anoPublicacao")
    val anoPublicacao: Int?,
    @ColumnInfo(name = "capaUrl")
    val capaUrl: String?,
    @ColumnInfo(name = "avaliacao")
    val avaliacao: String?
)