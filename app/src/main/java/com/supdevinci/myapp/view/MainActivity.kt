package com.supdevinci.myapp.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.os.Handler
import android.os.Looper
import com.supdevinci.myapp.R


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

        validerButton.setOnClickListener {
            validerButton.background = AppCompatResources.getDrawable(this,
                R.drawable.button_background_green
            )

            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this, CategoryActivity::class.java)
                startActivity(intent)
            }, 1000)
        }
    }
}