package com.supdevinci.myapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.supdevinci.myapp.data.QuestionRepository
import com.supdevinci.myapp.model.Question
import com.supdevinci.myapp.model.QuestionResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuestionViewModel : ViewModel() {

    private val repository = QuestionRepository()

    private val _questions = MutableLiveData<List<Question>>()
    val questions: LiveData<List<Question>> = _questions

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun loadQuestions(category: String) {
        repository.fetchQuestions(category).enqueue(object : Callback<QuestionResponse> {
            override fun onResponse(call: Call<QuestionResponse>, response: Response<QuestionResponse>) {
                if (response.isSuccessful) {
                    _questions.value = response.body()?.results ?: emptyList()
                    _error.value = null
                } else {
                    _error.value = "Erreur API: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<QuestionResponse>, t: Throwable) {
                _error.value = t.message
            }
        })
    }
}
