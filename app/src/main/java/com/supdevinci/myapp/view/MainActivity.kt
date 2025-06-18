package com.supdevinci.myapp.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.os.Handler
import android.os.Looper
import com.supdevinci.myapp.R
import androidx.room.Room
import com.supdevinci.myapp.data.AppDatabase
import com.supdevinci.myapp.model.LeaderboardEntry
import androidx.appcompat.widget.AppCompatImageButton
import com.supdevinci.myapp.data.VarProvider


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val validerButton = findViewById<Button>(R.id.validerButton)
        val enterEmail = findViewById<EditText>(R.id.enterEmail)

        validerButton.setOnClickListener {
            VarProvider.username= enterEmail.text.toString().trim()

            if (VarProvider.username.isEmpty()) {
                Toast.makeText(this, "Veuillez entrer un pseudo", Toast.LENGTH_SHORT).show()
            } else {
                validerButton.background = AppCompatResources.getDrawable(this,
                    R.drawable.button_background_green
                )

                Handler(Looper.getMainLooper()).postDelayed({
                    val intent = Intent(this, CategoryActivity::class.java)
                    startActivity(intent)
                }, 1000)
            }
        }

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "app_database"
        )
            .fallbackToDestructiveMigration()
            .build()

        val buttonLeaderboard = findViewById<AppCompatImageButton>(R.id.buttonLeaderboard)
        buttonLeaderboard.setOnClickListener {
            val intent = Intent(this, LeaderboardActivity::class.java)
            startActivity(intent)
        }
    }
}
