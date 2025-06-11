package com.saad.cse.flashcard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ScoreHistoryAdapter(private val scoreList: List<QuizScore>) :
    RecyclerView.Adapter<ScoreHistoryAdapter.ScoreViewHolder>() {

    class ScoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvScoreDate: TextView = itemView.findViewById(R.id.tvScoreDate)
        val tvScoreResult: TextView = itemView.findViewById(R.id.tvScoreResult)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_score_history, parent, false)
        return ScoreViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        val quizScore = scoreList[position]

        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        val dateString = dateFormat.format(Date(quizScore.timestamp))
        holder.tvScoreDate.text = holder.itemView.context.getString(R.string.score_item_format, dateString, quizScore.score, quizScore.totalQuestions)

        holder.tvScoreResult.text = holder.itemView.context.getString(R.string.current_score, quizScore.score, quizScore.totalQuestions)
    }

    override fun getItemCount(): Int = scoreList.size
}
