package com.supdevinci.myapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "leaderboard")
data class LeaderboardEntry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val username: String,
    val score: Int,
    val createdAt: Date = Date(),
    val deletedAt: Date? = null
)

