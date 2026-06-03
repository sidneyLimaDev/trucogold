package com.example.trucogold

data class Match(
    val team1Name: String,
    val team2Name: String,
    val team1Score: Int,
    val team2Score: Int,
    val date: String,
    val team1Won: Boolean
)