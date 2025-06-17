package com.supdevinci.myapp.view

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
import com.supdevinci.myapp.R
import com.supdevinci.myapp.model.Question
import com.supdevinci.myapp.view.adapter.QAAdapter
import com.supdevinci.myapp.viewmodel.QuestionViewModel

class QuestionActivity : AppCompatActivity() {

    private lateinit var viewModel: QuestionViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: QAAdapter
    private var response : String = ""
    private var score: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_question)

        viewModel = ViewModelProvider(this).get(QuestionViewModel::class.java)
        val questionTextView = findViewById<TextView>(R.id.questionText)

        recyclerView = findViewById(R.id.responseDisplay)
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.questions.observe(this) { questions ->
            if (questions.isNullOrEmpty()) {
                Toast.makeText(this, "Aucune réponse disponible.", Toast.LENGTH_SHORT).show()
                adapter.updateData(emptyList())
            } else {
                questionTextView.text = decodeBase64(questions[0].question)
                response = decodeBase64(questions[0].correct_answer)
                val allResponses = (questions[0].incorrect_answers + questions[0].correct_answer)
                    .map { decodeBase64(it) }
                    .shuffled()

                adapter = QAAdapter(allResponses, response) { clickedResponse ->
                    println("Tu as cliqué : $clickedResponse (réponse : $response)")
                }

                showQuestionAt(0, questions)

                recyclerView.adapter = adapter

                adapter.updateData(allResponses)
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
            Toast.makeText(this, "Fin des questions !", Toast.LENGTH_LONG).show()
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
            println("Tu as cliqué : $clickedResponse (réponse : $correctAnswer)")
            if(clickedResponse==response){
                score +=1
            }
            Handler(Looper.getMainLooper()).postDelayed({
                showQuestionAt(index + 1, questions)
            }, 1000)
            /*
            if(questions.size>=index+1){

            }*/
        }

        recyclerView.adapter = adapter
        adapter.updateData(allResponses)
    }
}
