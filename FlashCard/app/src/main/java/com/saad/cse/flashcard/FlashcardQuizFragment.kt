package com.saad.cse.flashcard

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.util.Locale

class FlashcardQuizFragment : Fragment() {

    private lateinit var tvQuestion: TextView
    private lateinit var etAnswer: EditText
    private lateinit var btnCheckAnswer: Button
    private lateinit var btnNextCard: Button
    private lateinit var btnStartNewQuiz: Button
    private lateinit var tvFeedback: TextView
    private lateinit var tvQuizScore: TextView
    private lateinit var quizProgressBar: ProgressBar
    private lateinit var tvNoFlashcards: TextView

    private var flashcards: MutableList<Flashcard> = mutableListOf()
    private var currentCardIndex: Int = 0
    private var correctAnswers: Int = 0
    private var totalQuestions: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_flashcard_quiz, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvQuestion = view.findViewById(R.id.tvQuestion)
        etAnswer = view.findViewById(R.id.etAnswer)
        btnCheckAnswer = view.findViewById(R.id.btnCheckAnswer)
        btnNextCard = view.findViewById(R.id.btnNextCard)
        btnStartNewQuiz = view.findViewById(R.id.btnStartNewQuiz)
        tvFeedback = view.findViewById(R.id.tvFeedback)
        tvQuizScore = view.findViewById(R.id.tvQuizScore)
        quizProgressBar = view.findViewById(R.id.quizProgressBar)
        tvNoFlashcards = view.findViewById(R.id.tvNoFlashcards)

        setupListeners()
        loadFlashcards() // Load from DataRepository
    }

    private fun setupListeners() {
        btnCheckAnswer.setOnClickListener {
            checkAnswer()
        }

        btnNextCard.setOnClickListener {
            displayNextCard()
        }

        btnStartNewQuiz.setOnClickListener {
            startNewQuiz()
        }

        etAnswer.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                tvFeedback.visibility = View.GONE
                btnCheckAnswer.visibility = View.VISIBLE
                btnNextCard.visibility = View.GONE
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun loadFlashcards() {
        quizProgressBar.visibility = View.VISIBLE
        tvNoFlashcards.visibility = View.GONE
        tvQuestion.text = getString(R.string.quiz_loading_flashcards)
        etAnswer.isEnabled = false
        btnCheckAnswer.isEnabled = false
        btnNextCard.isEnabled = false
        btnStartNewQuiz.visibility = View.GONE

        flashcards = DataRepository.getAllFlashcards().toMutableList()

        quizProgressBar.visibility = View.GONE
        if (flashcards.isNotEmpty()) {
            startNewQuiz()
        } else {
            tvNoFlashcards.visibility = View.VISIBLE
            tvQuestion.text = "" // Clear loading text
            tvQuizScore.visibility = View.GONE
            etAnswer.visibility = View.GONE
            btnCheckAnswer.visibility = View.GONE
            btnNextCard.visibility = View.GONE
            btnStartNewQuiz.visibility = View.GONE
            Toast.makeText(context, getString(R.string.no_flashcards_available), Toast.LENGTH_LONG).show()
        }
    }

    private fun startNewQuiz() {
        correctAnswers = 0
        currentCardIndex = 0
        flashcards.shuffle()
        totalQuestions = flashcards.size
        updateScoreDisplay()
        tvFeedback.visibility = View.GONE
        btnNextCard.visibility = View.GONE
        btnStartNewQuiz.visibility = View.GONE
        etAnswer.isEnabled = true
        etAnswer.setText("")
        btnCheckAnswer.isEnabled = true
        btnCheckAnswer.visibility = View.VISIBLE
        tvNoFlashcards.visibility = View.GONE
        tvQuizScore.visibility = View.VISIBLE
        etAnswer.visibility = View.VISIBLE
        tvQuestion.visibility = View.VISIBLE

        if (flashcards.isNotEmpty()) {
            displayNextCard()
        } else {
            tvNoFlashcards.visibility = View.VISIBLE
            tvQuestion.text = "" // Clear question if no cards
            tvQuizScore.visibility = View.GONE
            etAnswer.visibility = View.GONE
            btnCheckAnswer.visibility = View.GONE
            btnNextCard.visibility = View.GONE
            btnStartNewQuiz.visibility = View.GONE
        }
    }

    private fun displayNextCard() {
        tvFeedback.visibility = View.GONE
        etAnswer.setText("")
        etAnswer.isEnabled = true
        btnCheckAnswer.visibility = View.VISIBLE
        btnNextCard.visibility = View.GONE
        btnCheckAnswer.isEnabled = true

        if (currentCardIndex < flashcards.size) {
            val currentCard = flashcards[currentCardIndex]
            tvQuestion.text = currentCard.question
            updateScoreDisplay()
            currentCardIndex++
        } else {
            // Quiz finished
            showQuizResults()
        }
    }

    private fun checkAnswer() {
        val userAnswer = etAnswer.text.toString().trim().lowercase(Locale.getDefault())
        val correctAnswer = flashcards[currentCardIndex - 1].answer.trim().lowercase(Locale.getDefault())

        etAnswer.isEnabled = false

        if (userAnswer == correctAnswer) {
            correctAnswers++
            tvFeedback.text = getString(R.string.quiz_correct)
            tvFeedback.setTextColor(resources.getColor(R.color.green_correct, null))
        } else {
            tvFeedback.text = getString(R.string.quiz_incorrect, flashcards[currentCardIndex - 1].answer)
            tvFeedback.setTextColor(resources.getColor(R.color.red_incorrect, null))
        }
        tvFeedback.visibility = View.VISIBLE
        btnCheckAnswer.visibility = View.GONE
        btnNextCard.visibility = View.VISIBLE
        btnNextCard.isEnabled = true
        updateScoreDisplay()
    }

    private fun updateScoreDisplay() {
        tvQuizScore.text = getString(R.string.current_score, correctAnswers, totalQuestions)
    }

    private fun showQuizResults() {
        tvQuestion.text = getString(R.string.quiz_completed_title)
        etAnswer.visibility = View.GONE
        btnCheckAnswer.visibility = View.GONE
        btnNextCard.visibility = View.GONE
        tvFeedback.visibility = View.VISIBLE
        tvFeedback.text = getString(R.string.quiz_completed_message, correctAnswers, totalQuestions)
        tvFeedback.setTextColor(resources.getColor(R.color.primary_color, null))
        btnStartNewQuiz.visibility = View.VISIBLE
        btnStartNewQuiz.isEnabled = true

        saveQuizScore(correctAnswers, totalQuestions)
    }

    private fun saveQuizScore(score: Int, total: Int) {
        val quizScore = QuizScore(score = score, totalQuestions = total)
        DataRepository.addQuizScore(quizScore)
        Toast.makeText(context, "Quiz score saved!", Toast.LENGTH_SHORT).show()
    }
}
