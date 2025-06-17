package com.supdevinci.myapp.data

import androidx.room.*
import com.supdevinci.myapp.model.LeaderboardEntry

@Dao
interface LeaderboardDao {

    @Query("SELECT * FROM leaderboard WHERE deletedAt IS NULL ORDER BY score DESC")
    fun getAllActiveEntries(): List<LeaderboardEntry>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entry: LeaderboardEntry)

    @Update
    fun update(entry: LeaderboardEntry)

    @Query("UPDATE leaderboard SET deletedAt = :timestamp WHERE id = :id")
    fun softDelete(id: Int, timestamp: Long)
}
