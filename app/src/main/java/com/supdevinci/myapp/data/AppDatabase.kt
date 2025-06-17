package com.supdevinci.myapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.supdevinci.myapp.model.LeaderboardEntry
import androidx.room.TypeConverters

@Database(entities = [LeaderboardEntry::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun leaderboardDao(): LeaderboardDao
}
