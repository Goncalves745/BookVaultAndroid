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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.bookvault.network.Book
import com.example.bookvault.viewmodel.BookViewModel
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.draw.clip
import com.example.bookvault.BD.Livro
import com.example.bookvault.BD.MainViewModel


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


@Composable
fun BooksScreen(viewModel: MainViewModel = viewModel(), onAddBookClick: () -> Unit, onUserClick: () -> Unit) {
    val books by viewModel.allLivros.observeAsState(emptyList())
    var showDialog by remember { mutableStateOf(false) }
    var bookToDelete by remember { mutableStateOf<Livro?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF222831))
    ) {
        BookVaultAppBarIndex(onAddBookClick = onAddBookClick, onUserClick = onUserClick)

        if (books.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            ) {
                Text(
                    text = "No books available in the library",
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                items(books) { book ->
                    BookItem(book = book) {
                        showDialog = true
                        bookToDelete = book
                    }
                }
            }
        }

        if (showDialog && bookToDelete != null) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                confirmButton = {
                    Button(
                        onClick = {
                            bookToDelete?.let { viewModel.deleteLivro(it) }
                            showDialog = false
                        }
                    ) {
                        Text("Delete")
                    }
                },
                dismissButton = {
                    Button(onClick = { showDialog = false }) {
                        Text("Cancel")
                    }
                },
                title = { Text("Delete Book") },
                text = { Text("Are you sure you want to delete this book?") }
            )
        }
    }
}

@Composable
fun BookItem(book: Livro, onDeleteRequest: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color(0xFF393E46), shape = RoundedCornerShape(8.dp))
            .clickable { onDeleteRequest() }
            .padding(16.dp)
    ) {
        book.capaUrl?.let { coverUrl ->
            Image(
                painter = rememberImagePainter(data = coverUrl),
                contentDescription = book.nomeLivro,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp)
        ) {
            Text(
                text = book.nomeLivro,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(4.dp))
            book.autor?.let {
                Text(
                    text = "Author: $it",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            book.anoPublicacao?.let {
                Text(
                    text = "Published: $it",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            book.avaliacao?.let {
                Text(
                    text = "Review: $it",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun SettingsScreen(onAddBookClick: () -> Unit, onUserClick: () -> Unit,viewModel: MainViewModel = viewModel()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF222831))
            .padding(16.dp)
    ) {
        BookVaultAppBarIndex(onAddBookClick = onAddBookClick, onUserClick = onUserClick)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        ) {

            Button(
                onClick = {
                    viewModel.deleteAllBooks()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Delete All Books", color = Color.White)
            }
        }
    }
}

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBookScreen(navController: NavController, viewModel: BookViewModel = viewModel(),livroViewModel : MainViewModel = viewModel()) {
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
                            text = "Search Books",
                            color = Color.White
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back to Search",
                                tint = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFF222831)
                    )
                )
            } else {
                TopAppBar(
                    title = {
                        Text(
                            text = "Search Books",
                            color = Color.White
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back to Search",
                                tint = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFF222831)
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

                    LazyColumn {
                        items(books) { book ->
                            BookItem(book = book, onClick = { selectedBook = book })
                        }
                    }
                } else {
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
                                    color = Color.Cyan
                                )
                            },
                            colors = TextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Cyan,
                                unfocusedIndicatorColor = Color.White
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        )
                        Spacer(modifier = Modifier.height(24.dp))

                        Button(onClick = {
                            selectedBook?.let { book ->
                                val livro = Livro(
                                    nomeLivro = book.title ?: "Unknown Title",
                                    autor = book.author_name?.joinToString(),
                                    anoPublicacao = book.first_publish_year,
                                    capaUrl = book.cover_i?.let { "https://covers.openlibrary.org/b/id/$it-M.jpg" },
                                    avaliacao = review
                                )
                                livroViewModel.insertLivro(livro)
                                selectedBook = null
                                review = ""
                            }
                        }) {
                            Button(
                                onClick = {
                                    selectedBook?.let { book ->
                                        val livro = Livro(
                                            nomeLivro = book.title ?: "Unknown Title",
                                            autor = book.author_name?.joinToString(),
                                            anoPublicacao = book.first_publish_year,
                                            capaUrl = book.cover_i?.let { "https://covers.openlibrary.org/b/id/$it-M.jpg" },
                                            avaliacao = review
                                        )
                                        livroViewModel.insertLivro(livro)
                                        selectedBook = null
                                        review = ""
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            ) {
                                Text("Add to Library", color = Color.White)
                            }
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
            .clickable { onClick(book) }
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

