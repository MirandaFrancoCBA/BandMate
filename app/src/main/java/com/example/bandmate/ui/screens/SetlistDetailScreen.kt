package com.example.bandmate.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bandmate.viewmodel.SetlistViewModel
import kotlinx.coroutines.launch

@SuppressLint("RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetlistDetailScreen(
    navController: NavController,
    viewModel: SetlistViewModel,
    setlistId: Int
) {
    val setlist = viewModel.setlists.find { it.id == setlistId }
    val coroutineScope = rememberCoroutineScope()
    var newSong by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }

    if (setlist == null) {
        Text("Setlist no encontrado.")
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(setlist.name) })
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = newSong,
                onValueChange = { newSong = it },
                label = { Text("Agregar canción") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    if (newSong.isNotBlank()) {
                        viewModel.addSongToSetlist(setlistId, newSong)
                        newSong = ""
                    }
                },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("Añadir")
            }

            Spacer(modifier = Modifier.height(16.dp))

            var songToDelete by remember { mutableStateOf<String?>(null) }

            LazyColumn {
                items(setlist.songs) { song ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Text(
                            text = song,
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 8.dp)
                        )
                        Button(
                            onClick = {
                                songToDelete = song
                            }
                        ) {
                            Text("Eliminar")
                        }
                    }
                }
            }

            if (songToDelete != null) {
                AlertDialog(
                    onDismissRequest = { songToDelete = null },
                    title = { Text("Confirmar eliminación") },
                    text = { Text("¿Seguro que querés eliminar \"${songToDelete}\" del setlist?") },
                    confirmButton = {
                        Button(
                            onClick = {
                                val deletedSong = songToDelete // ← Guardamos antes de borrar
                                viewModel.removeSongFromSetlist(setlistId, deletedSong!!)
                                songToDelete = null

                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("Canción eliminada: $deletedSong")
                                }
                            }
                        ) {
                            Text("Sí, eliminar")
                        }
                    },
                    dismissButton = {
                        Button(onClick = { songToDelete = null }) {
                            Text("Cancelar")
                        }
                    }
                )
            }
        }
    }
}
