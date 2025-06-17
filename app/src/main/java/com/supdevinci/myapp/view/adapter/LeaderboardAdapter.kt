package com.supdevinci.myapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.supdevinci.myapp.R
import com.supdevinci.myapp.model.LeaderboardEntry
import java.text.SimpleDateFormat
import java.util.*

class LeaderboardAdapter(private val leaderboardEntries: List<LeaderboardEntry>) :
    RecyclerView.Adapter<LeaderboardAdapter.LeaderboardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaderboardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_leaderboard, parent, false)
        return LeaderboardViewHolder(view)
    }

    override fun onBindViewHolder(holder: LeaderboardViewHolder, position: Int) {
        val entry = leaderboardEntries[position]
        holder.usernameTextView.text = entry.username
        holder.scoreTextView.text = "${entry.score}%"
        holder.dateTextView.text = formatDate(entry.createdAt)
    }

    override fun getItemCount(): Int = leaderboardEntries.size

    private fun formatDate(date: Date): String {
        val sdf = SimpleDateFormat("yy/MM/dd", Locale.getDefault())
        return sdf.format(date)
    }

    inner class LeaderboardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val usernameTextView: TextView = view.findViewById(R.id.textViewUsername)
        val scoreTextView: TextView = view.findViewById(R.id.textViewScore)
        val dateTextView: TextView = view.findViewById(R.id.textViewDate)
    }
}
