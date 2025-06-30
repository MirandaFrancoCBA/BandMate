package com.example.bandmate.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bandmate.viewmodel.SetlistViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetlistScreen(navController: NavController, viewModel: SetlistViewModel) {
    var newSetlistName by remember { mutableStateOf("") }
    val setlists by viewModel.setlists.collectAsState()


    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Mis Setlists") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if (newSetlistName.isNotBlank()) {
                    viewModel.addSetlist(newSetlistName)
                    newSetlistName = ""
                }
            }) {
                Text("+")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = newSetlistName,
                onValueChange = { newSetlistName = it },
                label = { Text("Nuevo Setlist") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(setlists) { setlist ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable {
                                navController.navigate("setlistDetail/${setlist.id}")
                            }
                    ) {
                        Text(
                            text = setlist.name,
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }

                }
                }
            }
        }
    }

