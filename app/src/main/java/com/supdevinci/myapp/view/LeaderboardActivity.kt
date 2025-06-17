package com.supdevinci.myapp.view

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.supdevinci.myapp.R
import com.supdevinci.myapp.data.AppDatabase
import com.supdevinci.myapp.model.LeaderboardEntry
import com.supdevinci.myapp.view.adapter.LeaderboardAdapter

class LeaderboardActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: LeaderboardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_leaderboard)

        recyclerView = findViewById(R.id.recyclerViewLeaderboard)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "app_database"
        )
            .fallbackToDestructiveMigration()
            .build()

        val leaderboardDao = db.leaderboardDao()

        Thread {
            val topScores = leaderboardDao.getTopScores(10)  // Obtenir les 10 premiers scores
            runOnUiThread {
                if (topScores.isNullOrEmpty()) {
                    Toast.makeText(this, "Pas encore de scores.", Toast.LENGTH_SHORT).show()
                } else {
                    adapter = LeaderboardAdapter(topScores)
                    recyclerView.adapter = adapter
                }
            }
        }.start()
    }
}
