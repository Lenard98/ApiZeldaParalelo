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


    val gameUrlToNameMap = games.associateBy { it.id }.mapValues { it.value.name }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(43, 114, 24))
            .padding(16.dp)
    ) {

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Buscar personajes", color = Color.Black) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Black,
            )
        )

        Spacer(modifier = Modifier.height(16.dp))


        errorMessage?.let {
            Snackbar(
                modifier = Modifier.padding(8.dp),
                content = { Text(text = it, color = Color.Black) },
                containerColor = Color.Yellow
            )
        }


        LazyColumn(modifier = Modifier.fillMaxSize()) {
            val filteredCharacters = characters.filter {
                it.name.contains(searchQuery.text, ignoreCase = true)
            }

            items(filteredCharacters) { character ->

                val gameNames = character.appearances.mapNotNull { url ->
                    val gameId = url.split("/").last()
                    gameUrlToNameMap[gameId]
                }

                CharacterItem(character.copy(appearances = gameNames))
            }
        }
    }


    LaunchedEffect(Unit) {
        viewModel.fetchCharacters()
        gameViewModel.fetchGames()
    }
}

@Composable
fun CharacterItem(character: Character) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(196, 175, 109))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = character.name,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = Color.Black,
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
