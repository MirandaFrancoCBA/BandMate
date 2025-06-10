package com.example.bandmate.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bandmate.viewmodel.MainViewModel

@Composable
fun RoleSelectionScreen(navController: NavController, viewModel: MainViewModel) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("¿Qué rol tienes en la banda?", fontSize = 22.sp)
            Spacer(modifier = Modifier.height(24.dp))

            listOf("Cantante", "Guitarrista", "Tecladista", "Baterista").forEach { rol ->
                Button(
                    onClick = {
                        viewModel.selectRole(rol)
                        navController.navigate("home")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text(rol)
                }
            }
        }
    }
}