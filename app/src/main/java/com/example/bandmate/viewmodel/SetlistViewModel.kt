package com.example.bandmate.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.bandmate.model.Setlist

class SetlistViewModel : ViewModel() {
    private var nextId = 1
    val setlists = mutableStateListOf<Setlist>()

    fun addSetlist(name: String) {
        setlists.add(Setlist(id = nextId++, name = name))
    }

    fun addSongToSetlist(setlistId: Int, song: String) {
        val index = setlists.indexOfFirst { it.id == setlistId }
        if (index != -1) {
            val current = setlists[index]
            setlists[index] = current.copy(songs = current.songs + song)
        }
    }
    fun removeSongFromSetlist(setlistId: Int, song: String) {
        val index = setlists.indexOfFirst { it.id == setlistId }
        if (index != -1) {
            val current = setlists[index]
            setlists[index] = current.copy(songs = current.songs - song)
        }
    }

}


