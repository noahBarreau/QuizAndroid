package com.supdevinci.myapp.services

import com.supdevinci.myapp.model.Question
import com.supdevinci.myapp.model.QuestionResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("api.php?amount=0&encode=base64")
    fun getNull(): Call<QuestionResponse>

    @GET("api.php?amount=3&encode=base64")
    fun getRandomQuestions(): Call<QuestionResponse>
}
