package com.example.bookvault.BD

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [(Livro::class)], version = 1, exportSchema = false)
abstract class LivroRoomDatabase: RoomDatabase() {
    abstract fun livroDAO(): LivroDAO
    companion object {
        private var INSTANCE: LivroRoomDatabase? = null
        fun getInstance(context: Context): LivroRoomDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        LivroRoomDatabase::class.java,
                        "livros_database"
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
