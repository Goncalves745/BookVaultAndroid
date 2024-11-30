package com.example.bookvault

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.bookvault.network.Book
import com.example.bookvault.viewmodel.BookViewModel


// App Bar for the Home Screen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookVaultAppBar() {
    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        title = {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text(stringResource(id = R.string.BookVault), color = Color.White)
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF222831),
            titleContentColor = Color.White
        )
    )
}

// App Bar for Index Page (with Add and User buttons)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookVaultAppBarIndex(onAddBookClick: () -> Unit, onUserClick: () -> Unit) {
    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        title = {
            Text(
                stringResource(id = R.string.BookVault),
                color = Color.White,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        navigationIcon = {
            IconButton(onClick = onUserClick) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = stringResource(id = R.string.user),
                    tint = Color.White
                )
            }
        },
        actions = {
            IconButton(onClick = onAddBookClick) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.add_book),
                    tint = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF222831),
            titleContentColor = Color.White
        )
    )
}

// Screen for Home Page
@Composable
fun HomePageScreen(onAddBookClick: () -> Unit, onUserClick: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().background(Color(0xFF222831))) {
        BookVaultAppBarIndex(onAddBookClick = onAddBookClick, onUserClick = onUserClick)
        Column(modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center)) {
            Text(
                text = stringResource(id = R.string.HomePage),
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                fontSize = 18.sp
            )
        }
    }
}

// Screen for Books Page
@Composable
fun BooksScreen() {
    Column(modifier = Modifier.fillMaxSize().background(Color(0xFF222831))) {
        BookVaultAppBar()
        Column(modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center)) {
            Text(
                text = stringResource(id = R.string.Books),
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                fontSize = 18.sp
            )
        }
    }
}

// Screen for Settings Page
@Composable
fun SettingsScreen() {
    Column(modifier = Modifier.fillMaxSize().background(Color(0xFF222831))) {
        BookVaultAppBar()
        Column(modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center)) {
            Text(
                text = stringResource(id = R.string.Settings),
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                fontSize = 18.sp
            )
        }
    }
}

// User Screen with Back Button
@Composable
fun UserScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.align(Alignment.TopStart).padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = stringResource(id = R.string.back),
                tint = Color.Black
            )
        }

        Column(modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center)) {
            Text(
                text = stringResource(id = R.string.add_book),
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun AddBookScreen(navController: NavController, viewModel: BookViewModel = viewModel()) {
    var query by remember { mutableStateOf("") }
    val books by viewModel.books.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Back button
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(
                imageVector = androidx.compose.material.icons.Icons.Default.ArrowBack,
                contentDescription = "Back"
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Search bar with a search button
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            TextField(
                value = query,
                onValueChange = { query = it },
                label = { Text("Search Books") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { viewModel.searchBooks(query) }) {
                Text("Search")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // List of books
        LazyColumn {
            items(books) { book ->
                BookItem(book = book, onClick = { selectedBook ->
                    // This is where you navigate to the BookPageScreen with the book ID
                    val bookId = selectedBook.key
                    if (bookId.isNotEmpty()) {
                        navController.navigate("bookPage/$bookId")
                    } else {
                        Log.e("Navigation", "Invalid or empty bookId, cannot navigate")
                    }
                })
            }
        }
    }
}

@Composable
fun BookItem(book: Book, onClick: (Book) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick(book) } // Trigger navigation when clicked
    ) {
        val coverUrl = book.cover_i?.let { "https://covers.openlibrary.org/b/id/$it-M.jpg" }
        Image(
            painter = rememberImagePainter(data = coverUrl),
            contentDescription = book.title,
            modifier = Modifier.size(80.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.align(Alignment.CenterVertically)) {
            Text(text = book.title ?: "Unknown Title", fontWeight = FontWeight.Bold)
            Text(text = "Author(s): ${book.author_name?.joinToString() ?: "Unknown"}")
            Text(text = "First Published: ${book.first_publish_year ?: "N/A"}")
        }
    }
}

@Composable
fun BookPageScreen(bookId: String) {
    // Use the bookId to fetch additional details if needed
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Book Details for $bookId", fontWeight = FontWeight.Bold)
        // Display more detailed information about the book here
    }
}