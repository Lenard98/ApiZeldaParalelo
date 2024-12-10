package com.example.apizeldaparalelo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.apizeldaparalelo.Modelos.Monster
import com.example.apizeldaparalelo.implementacionAPI.GameViewModel
import com.example.apizeldaparalelo.implementacionAPI.MonsterViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonsterScreen(viewModel: MonsterViewModel, gameViewModel: GameViewModel) {
    var searchQuery by remember { mutableStateOf("") }
    val monsters by viewModel.monsters.collectAsState(initial = emptyList())
    val errorMessage by viewModel.error.collectAsState()
    val games by gameViewModel.games.collectAsState(initial = emptyList())

    // Crear un mapa de las URLs a los nombres de los juegos
    val gameUrlToNameMap = games.associateBy { it.id }.mapValues { it.value.name }

    // Barra de búsqueda y lista de monstruos
    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = Color(43, 114, 24)) // Color de fondo verde
        .padding(16.dp)
    ) {
        // Barra de búsqueda
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Buscar monstruos", color = Color.Black) },
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

        // Lista de monstruos filtrados por búsqueda
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            val filteredMonsters = monsters.filter {
                it.name.contains(searchQuery, ignoreCase = true)
            }

            items(filteredMonsters) { monster ->
                // Mapear las URLs de las apariciones a los nombres de los juegos
                val gameNames = monster.appearances.mapNotNull { url ->
                    val gameId = url.split("/").last() // Suponiendo que el id está al final de la URL
                    gameUrlToNameMap[gameId]
                }

                MonsterItem(monster.copy(gameNames = gameNames)) // Actualizar con los nombres de los juegos
            }
        }
    }

    // Llamada inicial para obtener los monstruos
    LaunchedEffect(Unit) {
        viewModel.fetchMonsters()
        gameViewModel.fetchGames()
    }
}

@Composable
fun MonsterItem(monster: Monster) {
    // Card con el mismo color de fondo que GameScreen
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp), // Ajustar el padding según lo necesario
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(196, 175, 109)) // Color de fondo de la card
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Nombre del monstruo
            Text(
                text = monster.name,
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black,
                overflow = TextOverflow.Visible // Asegura que el texto no se corte
            )

            // Descripción del monstruo
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = monster.description,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Black,
                overflow = TextOverflow.Visible // El texto no se corta
            )

            // Nombres de los juegos
            Spacer(modifier = Modifier.height(8.dp))
            monster.gameNames.forEach { gameName ->
                Text(
                    text = "Aparece en: $gameName",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Black,
                    modifier = Modifier.padding(top = 4.dp) // Añadir espacio entre los elementos
                )
            }
        }
    }
}
