package com.supdevinci.myapp.data

import androidx.room.*
import com.supdevinci.myapp.model.LeaderboardEntry

@Dao
interface LeaderboardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entry: LeaderboardEntry)

    @Update
    fun update(entry: LeaderboardEntry)

    @Query("UPDATE leaderboard SET deletedAt = :timestamp WHERE id = :id")
    fun softDelete(id: Int, timestamp: Long)

    @Query("SELECT * FROM leaderboard WHERE deletedAt IS NULL ORDER BY score DESC LIMIT :limit")
    fun getTopScores(limit: Int): List<LeaderboardEntry>
}
