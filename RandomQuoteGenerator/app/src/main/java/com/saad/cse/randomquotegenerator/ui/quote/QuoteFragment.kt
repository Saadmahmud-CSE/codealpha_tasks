package com.saad.cse.randomquotegenerator.ui.quote

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.saad.cse.randomquotegenerator.R

class QuoteFragment : Fragment() {

    private lateinit var tvQuote: TextView
    private val quotes = listOf(
        "The best way to predict the future is to invent it.",
        "Believe in yourself and all that you are.",
        "You miss 100% of the shots you don’t take.",
        "Be the change that you wish to see in the world.",
        "It always seems impossible until it’s done.",
        "The only way to do great work is to love what you do.",
        "Success is not final, failure is not fatal: it is the courage to continue that counts.",
        "The future belongs to those who believe in the beauty of their dreams.",
        "Strive not to be a success, but rather to be of value.",
        "The mind is everything. What you think you become."
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quote, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvQuote = view.findViewById(R.id.tvQuote)

        view.findViewById<Button>(R.id.btnQuote).setOnClickListener {
            val randomQuote = quotes.random()
            tvQuote.text = randomQuote
        }

        view.findViewById<Button>(R.id.btnShare).setOnClickListener {
            val quoteText = tvQuote.text.toString()
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, quoteText)
            }
            startActivity(Intent.createChooser(shareIntent, "Share Quote"))
        }
    }
}