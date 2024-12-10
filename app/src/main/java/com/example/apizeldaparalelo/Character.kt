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
import androidx.compose.ui.text.style.TextOverflow
import com.example.apizeldaparalelo.Modelos.Character
import com.example.apizeldaparalelo.implementacionAPI.CharacterViewModel
import com.example.apizeldaparalelo.implementacionAPI.GameViewModel
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


@Composable
fun CharacterScreen(viewModel: CharacterViewModel, gameViewModel: GameViewModel) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    val characters by viewModel.characters.collectAsState(initial = emptyList())
    val errorMessage by viewModel.error.collectAsState()
    val games by gameViewModel.games.collectAsState(initial = emptyList())

    // Crear un mapa de las URLs a los nombres de los juegos
    val gameUrlToNameMap = games.associateBy { it.id }.mapValues { it.value.name }

    // Fondo de la pantalla con el mismo color de GameScreen
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(43, 114, 24)) // Mismo color de fondo
            .padding(16.dp)
    ) {
        // Barra de búsqueda con el fondo blanco
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Buscar personajes", color = Color.Black) }, // Etiqueta en color negro
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White, // Fondo blanco
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                focusedBorderColor = Color.Black, // Borde negro cuando está enfocado
                unfocusedBorderColor = Color.Gray, // Borde gris cuando no está enfocado
                focusedLabelColor = Color.Black, // Color de la etiqueta cuando está enfocado
                unfocusedLabelColor = Color.Black, // Color de la etiqueta cuando no está enfocado
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar mensaje de error si existe, con los mismos colores de GameScreen
        errorMessage?.let {
            Snackbar(
                modifier = Modifier.padding(8.dp),
                content = { Text(text = it, color = Color.Black) },
                containerColor = Color.Yellow
            )
        }

        // Lista de personajes
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            val filteredCharacters = characters.filter {
                it.name.contains(searchQuery.text, ignoreCase = true)
            }

            items(filteredCharacters) { character ->
                // Mapear las apariciones a los nombres de los juegos
                val gameNames = character.appearances.mapNotNull { url ->
                    val gameId = url.split("/").last()
                    gameUrlToNameMap[gameId]
                }

                CharacterItem(character.copy(appearances = gameNames)) // Actualizar con los nombres de los juegos
            }
        }
    }

    // Llamada inicial para obtener datos
    LaunchedEffect(Unit) {
        viewModel.fetchCharacters()
        gameViewModel.fetchGames() // Asegurarse de tener la lista de juegos
    }
}

@Composable
fun CharacterItem(character: Character) {
    // Card con el mismo color de fondo que GameScreen
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(196, 175, 109)) // Color de fondo de la card
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Nombre del personaje en negrita
            Text(
                text = character.name,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold // Aquí aplicamos la negrita
                ),
                color = Color.Black, // Color del texto en negro
                overflow = TextOverflow.Visible
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = character.description,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Black,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Mostrar género y raza si están disponibles
            if (character.gender != null) {
                Text(
                    text = "Género: ${character.gender}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Black
                )
            }
            if (character.race != null) {
                Text(
                    text = "Raza: ${character.race}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Black
                )
            }

            // Mostrar los juegos donde aparece el personaje
            character.appearances.forEach { gameName ->
                Text(
                    text = "Aparece en: $gameName",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Black,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}
