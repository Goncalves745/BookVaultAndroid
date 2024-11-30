package com.example.bookvault

import com.example.bookvault.R

sealed class Destino(val route: String, val icon: Int, val title: String) {
    object HomePageScreen : Destino(route = "HomePage", icon = R.drawable.baseline_home_24, title = "Home")
    object BooksScreen : Destino(route = "Books", icon = R.drawable.baseline_view_headline_24, title = "Books")
    object SettingsScreen : Destino(route = "Settings", icon = R.drawable.baseline_settings_24, title = "Settings")
    object UserScreen : Destino(route = "user", icon = R.drawable.baseline_account_circle_24, title = "User")
    object AddBook : Destino(route = "addBook", icon = R.drawable.baseline_add_24, title = "Add Book")

    object BookPage : Destino(route = "bookPage/{bookId}", icon = R.drawable.baseline_add_24, title = "Book Page") {
        fun createRoute(bookId: String): String {
            return "bookPage/${bookId.trim().removePrefix("/").replace("//", "/")}" // Ensure no leading slashes or double slashes
        }
    }



    companion object {
        val toList = listOf(HomePageScreen, BooksScreen, SettingsScreen)
    }
}