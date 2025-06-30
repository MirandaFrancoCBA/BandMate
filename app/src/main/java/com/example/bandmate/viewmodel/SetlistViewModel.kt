package com.example.bandmate.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.bandmate.data.local.AppDatabase
import com.example.bandmate.data.local.entities.Setlist
import com.example.bandmate.data.local.entities.SetlistWithSongs
import com.example.bandmate.data.local.entities.Song
import com.example.bandmate.repository.SetlistRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.Flow

class SetlistViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: SetlistRepository

    private val _setlists = MutableStateFlow<List<Setlist>>(emptyList())
    val setlists: StateFlow<List<Setlist>> get() = _setlists

    private val _currentSetlist = MutableStateFlow<SetlistWithSongs?>(null)
    val currentSetlist: StateFlow<SetlistWithSongs?> get() = _currentSetlist

    init {
        val dao = AppDatabase.getDatabase(application).setlistDao()
        repository = SetlistRepository(dao)

        // Cargar setlists al iniciar
        loadAllSetlists()
    }

    private fun loadAllSetlists() {
        viewModelScope.launch {
            _setlists.value = repository.getAllSetlists()
        }
    }

    fun loadSetlist(setlistId: Int) {
        viewModelScope.launch {
            _currentSetlist.value = repository.getSetlistWithSongs(setlistId)
        }
    }

    fun addSetlist(name: String) {
        viewModelScope.launch {
            val newSetlist = Setlist(name = name)
            repository.insertSetlist(newSetlist)
            loadAllSetlists()
        }
    }

    fun addSongToSetlist(setlistId: Int, title: String) {
        viewModelScope.launch {
            val newSong = Song(
                setlistId = setlistId,
                title = title,
                lyrics = "",
                chords = ""
            )
            repository.insertSong(newSong)
            loadSetlist(setlistId)
        }
    }

    fun removeSongFromSetlist(song: Song) {
        viewModelScope.launch {
            repository.deleteSong(song)
            loadSetlist(song.setlistId)
        }
    }

    fun updateSetlistName(setlistId: Int, newName: String) {
        viewModelScope.launch {
            val setlist = _currentSetlist.value?.setlist
            if (setlist != null && setlist.id == setlistId) {
                val updated = setlist.copy(name = newName)
                repository.updateSetlist(updated)
                loadSetlist(setlistId)
                loadAllSetlists()
            }
        }
    }

    fun updateSong(song: Song) {
        viewModelScope.launch {
            repository.updateSong(song)
            loadSetlist(song.setlistId)
        }
    }
    fun getSongById(songId: Int): Flow<Song?> {
        return repository.getSongById(songId)
    }
}


