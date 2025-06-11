package com.saad.cse.flashcard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ScoreHistoryFragment : Fragment() {

    private lateinit var rvScoreHistory: RecyclerView
    private lateinit var scoreHistoryProgressBar: ProgressBar
    private lateinit var tvNoScores: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_score_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvScoreHistory = view.findViewById(R.id.rvScoreHistory)
        scoreHistoryProgressBar = view.findViewById(R.id.scoreHistoryProgressBar)
        tvNoScores = view.findViewById(R.id.tvNoScores)

        rvScoreHistory.layoutManager = LinearLayoutManager(context)

        // Hide progress bar as there's no async loading for in-memory data
        scoreHistoryProgressBar.visibility = View.GONE

        loadScoreHistory() // Load from DataRepository
    }

    private fun loadScoreHistory() {
        scoreHistoryProgressBar.visibility = View.VISIBLE
        tvNoScores.visibility = View.GONE
        rvScoreHistory.visibility = View.GONE

        // Get scores from in-memory repository (already sorted by timestamp in DataRepository)
        val scores = DataRepository.getAllQuizScores()

        scoreHistoryProgressBar.visibility = View.GONE
        if (scores.isNotEmpty()) {
            rvScoreHistory.adapter = ScoreHistoryAdapter(scores)
            rvScoreHistory.visibility = View.VISIBLE
        } else {
            tvNoScores.visibility = View.VISIBLE
        }
    }
}
