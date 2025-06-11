package com.supdevinci.myapp.model

data class QuestionResponse(
    val response_code: Int,
    val results: List<Question>
)
