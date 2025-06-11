package com.supdevinci.myapp.model

data class Question(
    val id: Int,
    val type: String,
    val difficulty: String,
    val category: String,
    val question: String,
    val correct_answer: String,
    val incorrect_answers: List<String>
)