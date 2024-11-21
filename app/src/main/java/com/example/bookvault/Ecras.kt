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
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text


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
fun AddBookScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Back button
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = stringResource(id = R.string.back),
                tint = Color.Black
            )
        }
    }
}
