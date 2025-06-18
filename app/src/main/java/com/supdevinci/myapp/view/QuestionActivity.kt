package com.supdevinci.myapp.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Base64
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.supdevinci.myapp.R
import com.supdevinci.myapp.data.AppDatabase
import com.supdevinci.myapp.model.LeaderboardEntry
import com.supdevinci.myapp.model.Question
import com.supdevinci.myapp.view.adapter.QAAdapter
import com.supdevinci.myapp.viewmodel.QuestionViewModel

class QuestionActivity : AppCompatActivity() {

    private lateinit var viewModel: QuestionViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: QAAdapter
    private var response: String = ""
    private var score: Int = 0
    private lateinit var username: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_question)

        viewModel = ViewModelProvider(this).get(QuestionViewModel::class.java)
        val questionTextView = findViewById<TextView>(R.id.questionText)

        recyclerView = findViewById(R.id.responseDisplay)
        recyclerView.layoutManager = LinearLayoutManager(this)

        username = intent.getStringExtra("username") ?: "Anonymous"

        viewModel.questions.observe(this) { questions ->
            if (questions.isNullOrEmpty()) {
                Toast.makeText(this, "Aucune réponse disponible.", Toast.LENGTH_SHORT).show()
                adapter.updateData(emptyList())
            } else {
                questionTextView.text = decodeBase64(questions[0].question)
                response = decodeBase64(questions[0].correct_answer)
                showQuestionAt(0, questions)

                recyclerView.adapter = adapter
            }
        }

        val category = intent.getStringExtra("category").takeUnless { it.isNullOrEmpty() } ?: "random"
        viewModel.loadQuestions(category)
    }

    private fun decodeBase64(input: String): String {
        return try {
            val decodedBytes = Base64.decode(input, Base64.DEFAULT)
            String(decodedBytes, Charsets.UTF_8)
        } catch (e: IllegalArgumentException) {
            input
        }
    }

    private fun showQuestionAt(index: Int, questions: List<Question>) {
        if (index >= questions.size) {
            Toast.makeText(this, "Score : $score / ${questions.size}", Toast.LENGTH_LONG).show()
            saveScoreToDatabase(questions.size)

            val intent = Intent(this, LeaderboardActivity::class.java)
            startActivity(intent)
            return
        }

        val question = questions[index]
        val decodedQuestion = decodeBase64(question.question)
        val correctAnswer = decodeBase64(question.correct_answer)
        val allResponses = (question.incorrect_answers + question.correct_answer)
            .map { decodeBase64(it) }
            .shuffled()

        val questionTextView = findViewById<TextView>(R.id.questionText)
        questionTextView.text = decodedQuestion

        adapter = QAAdapter(allResponses, correctAnswer) { clickedResponse ->
            if (clickedResponse == correctAnswer) {
                score++
            }
            Handler(Looper.getMainLooper()).postDelayed({
                showQuestionAt(index + 1, questions)
            }, 1000)
        }

        recyclerView.adapter = adapter
        adapter.updateData(allResponses)
    }

    private fun saveScoreToDatabase(questionsSize: Int) {
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "app_database"
        )
            .fallbackToDestructiveMigration()
            .build()

        val leaderboardDao = db.leaderboardDao()
        val newEntry = LeaderboardEntry(username = username, score = (score*100)/questionsSize)

        Thread {
            leaderboardDao.insert(newEntry)
        }.start()
    }
}
