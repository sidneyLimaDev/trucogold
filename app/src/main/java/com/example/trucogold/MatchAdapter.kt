package com.example.trucogold

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class MatchAdapter(private val matches: List<Match>) :
    RecyclerView.Adapter<MatchAdapter.MatchViewHolder>() {

    class MatchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val layoutBackground: View = view.findViewById(R.id.layoutBackground)
        val tvDate: TextView = view.findViewById(R.id.tvMatchDate)
        val tvTeam1Label: TextView = view.findViewById(R.id.tvTeam1Label)
        val tvTeam1Score: TextView = view.findViewById(R.id.tvTeam1Score)
        val tvTeam2Label: TextView = view.findViewById(R.id.tvTeam2Label)
        val tvTeam2Score: TextView = view.findViewById(R.id.tvTeam2Score)
        val tvResultBadge: TextView = view.findViewById(R.id.tvResultBadge)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_match_history, parent, false)
        return MatchViewHolder(view)
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        val match = matches[position]

        holder.tvDate.text = match.date
        holder.tvTeam1Label.text = match.team1Name
        holder.tvTeam1Score.text = match.team1Score.toString()
        holder.tvTeam2Label.text = match.team2Name
        holder.tvTeam2Score.text = match.team2Score.toString()

        if (match.team1Won) {
            holder.layoutBackground.setBackgroundResource(R.drawable.bg_truco_card)
            holder.tvResultBadge.visibility = View.GONE
            
            // Highlight winner scores
            holder.tvTeam1Score.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.dark_secondary))
            holder.tvTeam1Label.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.dark_secondary))
            holder.tvTeam2Score.setTextColor(android.graphics.Color.WHITE)
            holder.tvTeam2Label.setTextColor(android.graphics.Color.WHITE)
        } else {
            holder.layoutBackground.setBackgroundResource(R.drawable.bg_truco_score)
            holder.tvResultBadge.visibility = View.VISIBLE
            
            // Highlight winner scores (team 2)
            holder.tvTeam2Score.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.dark_secondary))
            holder.tvTeam2Label.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.dark_secondary))
            holder.tvTeam1Score.setTextColor(android.graphics.Color.WHITE)
            holder.tvTeam1Label.setTextColor(android.graphics.Color.WHITE)
        }
    }

    override fun getItemCount() = matches.size
}