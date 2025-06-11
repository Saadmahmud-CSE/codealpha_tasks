package com.saad.cse.flashcard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment

class AddFlashcardFragment : Fragment() {

    private lateinit var etQuestionInput: EditText
    private lateinit var etAnswerInput: EditText
    private lateinit var btnAddFlashcard: Button
    private lateinit var addFlashcardProgressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_flashcard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etQuestionInput = view.findViewById(R.id.etQuestionInput)
        etAnswerInput = view.findViewById(R.id.etAnswerInput)
        btnAddFlashcard = view.findViewById(R.id.btnAddFlashcard)
        addFlashcardProgressBar = view.findViewById(R.id.addFlashcardProgressBar)

        // Hide progress bar as there's no async operation for in-memory data
        addFlashcardProgressBar.visibility = View.GONE

        btnAddFlashcard.setOnClickListener {
            addFlashcard()
        }
    }

    private fun addFlashcard() {
        val question = etQuestionInput.text.toString().trim()
        val answer = etAnswerInput.text.toString().trim()

        if (question.isEmpty() || answer.isEmpty()) {
            Toast.makeText(context, getString(R.string.flashcard_add_empty_fields), Toast.LENGTH_SHORT).show()
            return
        }

        // Add to in-memory repository
        val flashcard = Flashcard(question = question, answer = answer)
        DataRepository.addFlashcard(flashcard)

        Toast.makeText(context, getString(R.string.flashcard_added_success), Toast.LENGTH_SHORT).show()
        etQuestionInput.setText("")
        etAnswerInput.setText("")
    }
}
