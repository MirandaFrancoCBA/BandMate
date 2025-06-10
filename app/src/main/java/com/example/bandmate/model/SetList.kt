package com.example.bandmate.model

data class Setlist(
    val id: Int,
    val name: String,
    val songs: List<String> = emptyList()
)
