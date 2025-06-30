package com.example.bandmate.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bandmate.data.local.entities.Song
import com.example.bandmate.viewmodel.SetlistViewModel
import androidx.compose.material.icons.filled.Check

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongDetailScreen(
    navController: NavController,
    viewModel: SetlistViewModel,
    songId: Int
) {
    // Obtener la canción a partir del setlist actual
    val song = viewModel.currentSetlist.collectAsState().value?.songs?.find { it.id == songId }

    if (song == null) {
        Text("Canción no encontrada.")
        return
    }

    var title by remember { mutableStateOf(song.title) }
    var lyrics by remember { mutableStateOf(song.lyrics) }
    var chords by remember { mutableStateOf(song.chords) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar canción") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        val updated = song.copy(
                            title = title,
                            lyrics = lyrics,
                            chords = chords
                        )
                        viewModel.updateSong(updated)
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Default.Check, contentDescription = "Guardar")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = lyrics,
                onValueChange = { lyrics = it },
                label = { Text("Letra") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                maxLines = 10
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = chords,
                onValueChange = { chords = it },
                label = { Text("Acordes") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                maxLines = 6
            )
        }
    }
}