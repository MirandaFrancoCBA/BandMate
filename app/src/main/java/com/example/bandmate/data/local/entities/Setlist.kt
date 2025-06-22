package com.example.bandmate.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Setlist(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String
)