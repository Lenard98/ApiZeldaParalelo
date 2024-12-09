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


@Composable
fun GameScreen(viewModel: GameViewModel) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    val games by viewModel.games.collectAsState(initial = emptyList())
    val errorMessage by viewModel.error.collectAsState()

    // Fondo de pantalla
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0, 150, 23)) // Fondo verde
            .padding(16.dp)
    ) {
        // Barra de búsqueda sin color
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Buscar juegos", color = Color.Black) },
            modifier = Modifier.fillMaxWidth()
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

    // Llamada inicial a la API
    LaunchedEffect(Unit) {
        viewModel.fetchGames()
    }
}

@Composable
fun GameItem(game: Game) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(245, 241, 99)) // Color de la card
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = game.name,
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black, // Mantener el color del texto
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



