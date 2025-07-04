package com.example.bandmate.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bandmate.data.local.entities.Song
import com.example.bandmate.viewmodel.SetlistViewModel
import kotlinx.coroutines.launch

@SuppressLint("RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetlistDetailScreen(
    navController: NavController,
    viewModel: SetlistViewModel,
    setlistId: Int,
) {
    LaunchedEffect(setlistId) {
        viewModel.loadSetlist(setlistId)
    }

    val setlistWithSongs by viewModel.currentSetlist.collectAsState()
    val setlist = setlistWithSongs?.setlist
    val songs = setlistWithSongs?.songs ?: emptyList()
    val coroutineScope = rememberCoroutineScope()
    var newSong by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    var isEditing by remember { mutableStateOf(false) }
    var editedName by remember { mutableStateOf(setlist!!.name) }

    if (setlist == null) {
        Text("Setlist no encontrado.")
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (isEditing) {
                        OutlinedTextField(
                            value = editedName,
                            onValueChange = { editedName = it },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    } else {
                        Text(editedName)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                actions = {
                    if (isEditing) {
                        IconButton(onClick = {
                            isEditing = false
                        }) {
                            Icon(Icons.Default.Check, contentDescription = "Guardar")
                        }
                    } else {
                        IconButton(onClick = {
                            isEditing = true
                        }) {
                            Icon(Icons.Default.Edit, contentDescription = "Editar nombre")
                        }
                    }
                }
            )
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

            var songToDelete by remember { mutableStateOf<Song?>(null) }

            LazyColumn {
                items(songs) { song ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = song.title,
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    navController.navigate("songView/${song.id}")
                                }
                        )

                        IconButton(onClick = {
                            navController.navigate("songDetail/${song.id}")
                        }) {
                            Icon(Icons.Default.Edit, contentDescription = "Editar")
                        }

                        Button(onClick = { songToDelete = song }) {
                            Text("Eliminar")
                        }
                    }
                }
            }

            if (songToDelete != null) {
                AlertDialog(
                    onDismissRequest = { songToDelete = null },
                    title = { Text("Confirmar eliminación") },
                    text = { Text("¿Seguro que querés eliminar \"${songToDelete?.title}\" del setlist?") },
                    confirmButton = {
                        Button(
                            onClick = {
                                val deletedSong = songToDelete
                                viewModel.removeSongFromSetlist(deletedSong!!)
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
