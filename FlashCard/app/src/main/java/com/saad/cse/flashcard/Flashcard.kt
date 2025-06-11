package com.saad.cse.flashcard

// Data class to represent a single flashcard
data class Flashcard(
    var id: String = "", // Document ID from Firestore, useful for updates/deletes
    val question: String = "",
    val answer: String = ""
)
