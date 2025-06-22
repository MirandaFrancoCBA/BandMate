package com.example.bandmate.data.local.dao

import androidx.room.*

@Dao
interface SetlistDao {

    @Insert
    suspend fun insertSetlist(setlist: Setlist): Long

    @Insert
    suspend fun insertSong(song: Song)

    @Transaction
    @Query("SELECT * FROM Setlist WHERE id = :id")
    suspend fun getSetlistWithSongs(id: Int): SetlistWithSongs?

    @Query("SELECT * FROM Setlist")
    suspend fun getAllSetlists(): List<Setlist>

    @Delete
    suspend fun deleteSetlist(setlist: Setlist)

    @Delete
    suspend fun deleteSong(song: Song)

    @Update
    suspend fun updateSong(song: Song)

    @Update
    suspend fun updateSetlist(setlist: Setlist)
}