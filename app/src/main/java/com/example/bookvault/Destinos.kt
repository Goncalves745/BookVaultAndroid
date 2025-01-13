package com.example.bookvault


sealed class Destino(val route: String, val icon: Int, val title: String) {
    object BooksScreen : Destino(route = "Books", icon = R.drawable.baseline_view_headline_24, title = "Books")
    object SettingsScreen : Destino(route = "Settings", icon = R.drawable.baseline_settings_24, title = "Settings")
    object UserScreen : Destino(route = "user", icon = R.drawable.baseline_account_circle_24, title = "User")
    object AddBook : Destino(route = "addBook", icon = R.drawable.baseline_add_24, title = "Add Book")

    companion object {
        val toList = listOf(BooksScreen, SettingsScreen)
    }
}