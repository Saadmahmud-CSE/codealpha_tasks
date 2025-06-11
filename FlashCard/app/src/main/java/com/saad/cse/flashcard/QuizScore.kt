package com.saad.cse.flashcard

data class QuizScore(
    var id: String = "", // Document ID from Firestore
    val timestamp: Long = System.currentTimeMillis(), // When the quiz was taken
    val score: Int = 0,
    val totalQuestions: Int = 0
)
