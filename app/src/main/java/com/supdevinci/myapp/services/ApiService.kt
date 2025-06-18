package com.supdevinci.myapp.services

import com.supdevinci.myapp.model.QuestionResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("api.php?amount=0&encode=base64")
    fun getNull(): Call<QuestionResponse>

    @GET("api.php?amount=40&encode=base64")
    fun getRandomQuestions(): Call<QuestionResponse>

    @GET("api.php")
    fun getCategorieQuestions(
        @Query("amount") amount: Int = 20,
        @Query("category") category: Int,
        @Query("encode") encode: String = "base64"
    ): Call<QuestionResponse>

}
