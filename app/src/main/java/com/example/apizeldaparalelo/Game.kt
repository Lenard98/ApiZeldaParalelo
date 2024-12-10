package com.example.apizeldaparalelo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextOverflow
import com.example.apizeldaparalelo.Modelos.Game
import com.example.apizeldaparalelo.implementacionAPI.GameViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(viewModel: GameViewModel) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    val games by viewModel.games.collectAsState(initial = emptyList())
    val errorMessage by viewModel.error.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(43, 114, 24)) // Mismo color de fondo
            .padding(16.dp)
    ) {
        // Barra de búsqueda con fondo blanco
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Buscar juegos", color = Color.Black) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color.White, // Fondo blanco
                focusedBorderColor = Color.Black, // Borde negro cuando está enfocado
                unfocusedBorderColor = Color.Gray, // Borde gris cuando no está enfocado
                focusedLabelColor = Color.Black, // Color de la etiqueta cuando está enfocado
                unfocusedLabelColor = Color.Black // Color de la etiqueta cuando no está enfocado
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar mensaje de error si existe
        errorMessage?.let {
            Snackbar(
                modifier = Modifier.padding(8.dp),
                content = { Text(text = it, color = Color.Black) },
                containerColor = Color.Yellow
            )
        }

        // Lista de juegos
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            val filteredGames = games.filter {
                it.name.contains(searchQuery.text, ignoreCase = true)
            }

            items(filteredGames) { game ->
                GameItem(game)
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.fetchGames()
    }
}

@Composable
fun GameItem(game: Game) {
    // Card con el mismo color de fondo que GameScreen
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(196, 175, 109)) // Color de fondo de la card
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = game.name,
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black,
                overflow = TextOverflow.Visible
            )
            Text(
                text = "Desarrollador: ${game.developer}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Black
            )
            Text(
                text = "Publicado por: ${game.publisher}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Black
            )
            Text(
                text = "Fecha de lanzamiento: ${game.released_date}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = game.description,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Black,
                overflow = TextOverflow.Visible
            )
        }
    }
}
