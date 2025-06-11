package com.saad.cse.flashcard

import java.util.UUID

object DataRepository {
    val flashcards: MutableList<Flashcard> = mutableListOf()
    val quizScores: MutableList<QuizScore> = mutableListOf()

    init {
        if (flashcards.isEmpty()) {
            addFlashcard(Flashcard(question = "What is Kotlin?", answer = "Kotlin is a statically-typed programming language."))
            addFlashcard(Flashcard(question = "What is an Activity in Android?", answer = "A single screen with a user interface."))
            addFlashcard(Flashcard(question = "What is the capital of Bangladesh?", answer = "Dhaka"))
            addFlashcard(Flashcard(question = "20 + 68 = ?", answer = "88"))
            addFlashcard(Flashcard(question = "Does Kotlin support primitive Datatypes?", answer = "No"))
        }

        if (quizScores.isEmpty()) {
            addQuizScore(QuizScore(score = 3, totalQuestions = 5, timestamp = System.currentTimeMillis() - 86400000L)) // Yesterday
            addQuizScore(QuizScore(score = 4, totalQuestions = 5, timestamp = System.currentTimeMillis() - 172800000L)) // Two days ago
        }
    }

    fun addFlashcard(flashcard: Flashcard) {
        if (flashcard.id.isEmpty()) {
            flashcard.id = UUID.randomUUID().toString()
        }
        flashcards.add(flashcard)
    }

    fun getAllFlashcards(): List<Flashcard> {
        return flashcards.toList()
    }

    fun addQuizScore(quizScore: QuizScore) {
        if (quizScore.id.isEmpty()) {
            quizScore.id = UUID.randomUUID().toString()
        }
        quizScores.add(quizScore)
    }
    fun getAllQuizScores(): List<QuizScore> {
        return quizScores.sortedByDescending { it.timestamp }
    }
}
