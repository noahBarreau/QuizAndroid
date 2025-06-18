package com.supdevinci.myapp.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.supdevinci.myapp.R
import com.supdevinci.myapp.data.VarProvider

class CategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_category)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val logo = findViewById<ImageView>(R.id.logo)

        logo.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val random = findViewById<Button>(R.id.CategoryRandom)

        random.setOnClickListener {
            random.background = AppCompatResources.getDrawable(this,
                R.drawable.button_background_green
            )

            Handler(Looper.getMainLooper()).postDelayed({
                val intentCategory = Intent(this, QuestionActivity::class.java)
                VarProvider.category="random"
                startActivity(intentCategory)
            }, 1000)
        }

        val category = findViewById<Button>(R.id.categoryMythology)

        category.setOnClickListener {
            category.background = AppCompatResources.getDrawable(this,
                R.drawable.button_background_green
            )

            Handler(Looper.getMainLooper()).postDelayed({
                val intentCategory = Intent(this, QuestionActivity::class.java)
                VarProvider.category="mythologie"
                startActivity(intentCategory)
            }, 1000)
        }
    }
}