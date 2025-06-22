package com.example.bandmate.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = Setlist::class,
        parentColumns = ["id"],
        childColumns = ["setlistId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Song(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val setlistId: Int,
    val title: String,
    val lyrics: String,    // Texto de la letra (con o sin acordes incrustados)
    val chords: String     // Acordes por línea o en formato separado (pensado para transposición futura)
)