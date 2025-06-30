@file:Suppress("KotlinConstantConditions")

package com.example.bandmate.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bandmate.viewmodel.SetlistViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongViewScreen(
    navController: NavController,
    viewModel: SetlistViewModel,
    songId: Int
) {
    val song by viewModel.getSongById(songId).collectAsState(initial = null)
    var semitoneShift by remember { mutableIntStateOf(0) }

    if (song == null) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Canción no encontrada.")
        }
        return
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(song!!.title) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            val letraLines = song!!.lyrics.split("\n")
            val acordeLines = song!!.chords.split("\n")

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = { semitoneShift -= 2 }) { Text("-1") }
                Button(onClick = { semitoneShift -= 1 }) { Text("-½") }
                Button(onClick = { semitoneShift = 0 }) { Text("Reset") }
                Button(onClick = { semitoneShift += 1 }) { Text("+½") }
                Button(onClick = { semitoneShift += 2 }) { Text("+1") }
            }

            Spacer(modifier = Modifier.height(16.dp))

            for (i in letraLines.indices) {
                if (i < acordeLines.size) {
                    val transposedLine = acordeLines[i]
                        .split(" ")
                        .joinToString(" ") { chordPart: String ->
                            transposeChord(chordPart, semitoneShift)
                        }

                    Text(
                        text = AnnotatedString(transposedLine),
                        style = MaterialTheme.typography.bodyMedium,
                        fontFamily = FontFamily.Monospace,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Text(
                    text = letraLines[i],
                    style = MaterialTheme.typography.bodyLarge,
                    fontFamily = FontFamily.Monospace
                )

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

fun transposeChord(chord: String, semitoneShift: Int): String {
    val sharpNotes = listOf("C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B")
    val flatToSharp = mapOf("Db" to "C#", "Eb" to "D#", "Gb" to "F#", "Ab" to "G#", "Bb" to "A#")

    val regex = Regex("([A-G][b#]?)(.*)") // Acorde base + sufijo (como m, 7, etc.)
    val match = regex.matchEntire(chord) ?: return chord

    val root = flatToSharp[match.groupValues[1]] ?: match.groupValues[1]
    val suffix = match.groupValues[2]

    val index = sharpNotes.indexOf(root)
    if (index == -1) return chord

    val newIndex = (index + semitoneShift + 12) % 12
    return sharpNotes[newIndex] + suffix
}
