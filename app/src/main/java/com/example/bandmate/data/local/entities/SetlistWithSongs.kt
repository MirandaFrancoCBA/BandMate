package com.example.bandmate.data.local.entities

import androidx.room.Embedded
import androidx.room.Relation

data class SetlistWithSongs(
    @Embedded val setlist: Setlist,
    @Relation(
        parentColumn = "id",
        entityColumn = "setlistId"
    )
    val songs: List<Song>
)