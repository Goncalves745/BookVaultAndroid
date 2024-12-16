package com.example.bookvault

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
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.bookvault.network.Book
import com.example.bookvault.viewmodel.BookViewModel
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults


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

@OptIn(ExperimentalMaterial3Api::class) // or ExperimentalMaterialApi, depending on the API you're using
@Composable
fun AddBookScreen(navController: NavController, viewModel: BookViewModel = viewModel()) {
    var query by remember { mutableStateOf("") }
    val books by viewModel.books.collectAsState()
    var selectedBook by remember { mutableStateOf<Book?>(null) }
    var review by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            if (selectedBook != null) {
                TopAppBar(
                    title = {
                        Text(
                            text = "Book Details",
                            color = Color.White // Set the desired color here
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back to Search",
                                tint = Color.White // Set the icon color to white
                            )
                        }
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = Color(0xFF222831) // Set your desired background color
                    )
                )
            } else {
                TopAppBar(
                    title = {
                        Text(text = "Search Books", color = Color.White)
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back to Search",
                                tint = Color.White // Set the icon color to white
                            )
                        }
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = Color(0xFF222831) // Set your desired background color
                    )
                )
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .background(Color(0xFF222831))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                if (selectedBook == null) {
                    // Search UI
                    TextField(
                        value = query,
                        onValueChange = { query = it },
                        label = {
                            Text(
                                text = "Search Books",
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { viewModel.searchBooks(query) }) {
                        Text(text = "Search",color = Color.White)
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    // List of books
                    LazyColumn {
                        items(books) { book ->
                            BookItem(book = book, onClick = { selectedBook = book })
                        }
                    }
                } else {
                    // Book Details UI
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val book = selectedBook!!

                        Text(
                            text = book.title ?: "Unknown Title",
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        val coverUrl =
                            book.cover_i?.let { "https://covers.openlibrary.org/b/id/$it-M.jpg" }
                        Image(
                            painter = rememberImagePainter(data = coverUrl),
                            contentDescription = book.title,
                            modifier = Modifier.size(300.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Author(s): ${book.author_name?.joinToString() ?: "Unknown"}",color = Color.White)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "First Published: ${book.first_publish_year ?: "N/A"}",color = Color.White)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Review:",color = Color.White)
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = review,
                            onValueChange = { review = it },
                            label = {
                                Text(
                                    text = "Enter Review",
                                    color = Color.Cyan // Custom label color
                                )
                            },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedLabelColor = Color.White, // Color of label when focused
                                unfocusedLabelColor = Color.White, // Color of label when unfocused
                                focusedTextColor = Color.White, // Color of text when field is focused
                                unfocusedTextColor = Color.White, // Color of text when field is not focused
                                cursorColor = Color.White, // Cursor color
                                focusedBorderColor = Color.Cyan, // Focused border color
                                unfocusedBorderColor = Color.White // Unfocused border color
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        )
                        Spacer(modifier = Modifier.height(24.dp))

                        // Add to Library Button
                        Button(onClick = {
//                        viewModel.addBookToDatabase(book)
                            // Clear selection after adding the book
                            selectedBook = null
                        }) {
                            Text("Add to Library",color = Color.White)
                        }
                    }
                }
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
            Text(text = book.title ?: "Unknown Title", fontWeight = FontWeight.Medium,color = Color.White)
            Text(text = "Author(s): ${book.author_name?.joinToString() ?: "Unknown"}",color = Color.White)
            Text(text = "First Published: ${book.first_publish_year ?: "N/A"}",color = Color.White)
        }
    }
}

