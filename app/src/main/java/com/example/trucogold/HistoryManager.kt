package com.example.trucogold

import android.content.Context

class HistoryManager(context: Context) {
    private val prefs = context.getSharedPreferences("truco_history", Context.MODE_PRIVATE)

    fun saveMatch(match: Match) {
        val currentHistory = prefs.getString("matches", "") ?: ""
        val matchString = "${match.date}|${match.team1Name}|${match.team2Name}|${match.team1Score}|${match.team2Score}|${match.team1Won}"
        val newHistory = if (currentHistory.isEmpty()) matchString else "$matchString|||$currentHistory"
        prefs.edit().putString("matches", newHistory).apply()
    }

    fun getMatches(): List<Match> {
        val historyString = prefs.getString("matches", "") ?: ""
        if (historyString.isEmpty()) return emptyList()

        return historyString.split("|||").mapNotNull {
            val parts = it.split("|")
            if (parts.size == 6) {
                Match(
                    date = parts[0],
                    team1Name = parts[1],
                    team2Name = parts[2],
                    team1Score = parts[3].toInt(),
                    team2Score = parts[4].toInt(),
                    team1Won = parts[5].toBoolean()
                )
            } else null
        }
    }
}