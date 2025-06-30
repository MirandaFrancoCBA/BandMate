package com.example.bandmate.repository

import com.example.bandmate.data.local.dao.SetlistDao
import com.example.bandmate.data.local.entities.Setlist
import com.example.bandmate.data.local.entities.SetlistWithSongs
import com.example.bandmate.data.local.entities.Song
import kotlinx.coroutines.flow.Flow

class SetlistRepository(private val setlistDao: SetlistDao) {

    suspend fun insertSetlist(setlist: Setlist): Long {
        return setlistDao.insertSetlist(setlist)
    }

    suspend fun insertSong(song: Song) {
        setlistDao.insertSong(song)
    }

    suspend fun getSetlistWithSongs(id: Int): SetlistWithSongs? {
        return setlistDao.getSetlistWithSongs(id)
    }

    suspend fun getAllSetlists(): List<Setlist> {
        return setlistDao.getAllSetlists()
    }

    suspend fun deleteSetlist(setlist: Setlist) {
        setlistDao.deleteSetlist(setlist)
    }

    suspend fun deleteSong(song: Song) {
        setlistDao.deleteSong(song)
    }

    suspend fun updateSong(song: Song) {
        setlistDao.updateSong(song)
    }

    suspend fun updateSetlist(setlist: Setlist) {
        setlistDao.updateSetlist(setlist)
    }

    fun getSongById(songId: Int): Flow<Song?> {
        return setlistDao.getSongById(songId)
    }
}
