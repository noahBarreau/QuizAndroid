package com.supdevinci.myapp.data

import com.supdevinci.myapp.model.Question
import com.supdevinci.myapp.model.QuestionResponse
import com.supdevinci.myapp.services.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call

class QuestionRepository {
    private val apiService: ApiService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://opentdb.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)
    }

    fun fetchQuestions(category: String): Call<QuestionResponse> {
        return when(category) {
            "random" -> apiService.getRandomQuestions()
            else -> apiService.getNull() // adapter aussi si besoin
        }
    }
}

