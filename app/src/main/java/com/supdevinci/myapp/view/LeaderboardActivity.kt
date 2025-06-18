package com.supdevinci.myapp.view

import android.os.Bundle
import android.widget.Toast
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent
import androidx.room.Room
import com.supdevinci.myapp.R
import com.supdevinci.myapp.data.AppDatabase
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

        val separator = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        ContextCompat.getDrawable(this, R.drawable.separator)?.let {
            separator.setDrawable(it)
            recyclerView.addItemDecoration(separator)
        }

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "app_database"
        )
            .fallbackToDestructiveMigration()
            .build()

        val leaderboardDao = db.leaderboardDao()

        Thread {
            val topScores = leaderboardDao.getTopScores(10)
            runOnUiThread {
                if (topScores.isNullOrEmpty()) {
                    Toast.makeText(this, "Pas encore de scores.", Toast.LENGTH_SHORT).show()
                } else {
                    adapter = LeaderboardAdapter(topScores)
                    recyclerView.adapter = adapter
                }
            }
        }.start()

        val logo = findViewById<ImageView>(R.id.logo)
        logo.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
