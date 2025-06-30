package com.example.bandmate.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bandmate.data.local.entities.Setlist
import com.example.bandmate.data.local.entities.Song
import com.example.bandmate.data.local.dao.SetlistDao

@Database(entities = [Setlist::class, Song::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun setlistDao(): SetlistDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "bandmate_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}