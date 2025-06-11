package com.supdevinci.myapp.view

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.supdevinci.myapp.R
import com.supdevinci.myapp.viewmodel.QuestionViewModel
import android.util.Base64

class QuestionActivity : AppCompatActivity() {

    private lateinit var viewModel: QuestionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_question)

        viewModel = ViewModelProvider(this).get(QuestionViewModel::class.java)

        val questionTextView = findViewById<TextView>(R.id.questionText)

        viewModel.questions.observe(this) { questions ->
            if (questions.isNullOrEmpty()) {
                questionTextView.text = "Aucune question disponible."
            } else {
                val allQuestionsText = questions.joinToString(separator = "\n\n") { question ->
                    "Q: ${decodeBase64(question.question)}\nRéponse correcte: ${decodeBase64(question.correct_answer)}"
                }
                questionTextView.text = allQuestionsText
            }
        }

        viewModel.error.observe(this) { errorMsg ->
            errorMsg?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        }

        val category = intent.getStringExtra("category").takeUnless { it.isNullOrEmpty() } ?: "random"

        viewModel.loadQuestions(category)
    }

    fun decodeBase64(input: String): String {
        return try {
            val decodedBytes = Base64.decode(input, Base64.DEFAULT)
            String(decodedBytes, Charsets.UTF_8)
        } catch (e: IllegalArgumentException) {
            input
        }
    }
}
