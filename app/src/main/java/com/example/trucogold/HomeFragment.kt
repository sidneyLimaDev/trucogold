package com.example.trucogold

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HomeFragment : Fragment(), WinnerDialogFragment.WinnerDialogListener {

    private var scoreNos = 0
    private var scoreEles = 0
    private var quedasNos = 0
    private var quedasEles = 0
    private var maxScore = 12

    private lateinit var tvScoreNos: TextView
    private lateinit var tvScoreEles: TextView
    private lateinit var tvLabelNos: TextView
    private lateinit var tvLabelEles: TextView
    private lateinit var dotNos1: View
    private lateinit var dotNos2: View
    private lateinit var dotEles1: View
    private lateinit var dotEles2: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val prefs = requireActivity().getSharedPreferences("truco_prefs", Context.MODE_PRIVATE)

        tvScoreNos = view.findViewById(R.id.tvScoreNos)
        tvScoreEles = view.findViewById(R.id.tvScoreEles)
        tvLabelNos = view.findViewById(R.id.tvLabelNos)
        tvLabelEles = view.findViewById(R.id.tvLabelEles)
        dotNos1 = view.findViewById(R.id.dotNos1)
        dotNos2 = view.findViewById(R.id.dotNos2)
        dotEles1 = view.findViewById(R.id.dotEles1)
        dotEles2 = view.findViewById(R.id.dotEles2)

        // Configurações
        maxScore = prefs.getInt("max_score", 12)
        val team1Name = prefs.getString("team1_name", "")
        val team2Name = prefs.getString("team2_name", "")

        tvLabelNos.text = if (!team1Name.isNullOrBlank()) team1Name else "Time 1"
        tvLabelEles.text = if (!team2Name.isNullOrBlank()) team2Name else "Time 2"

        if (prefs.getBoolean("keep_screen_on", false)) {
            requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        } else {
            requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }

        // Nos buttons
        view.findViewById<Button>(R.id.btnNosPlus1).setOnClickListener { updateScoreNos(1) }
        view.findViewById<Button>(R.id.btnNosPlus3).setOnClickListener { updateScoreNos(3) }
        view.findViewById<Button>(R.id.btnNosPlus6).setOnClickListener { updateScoreNos(6) }
        view.findViewById<Button>(R.id.btnNosPlus9).setOnClickListener { updateScoreNos(9) }
        view.findViewById<Button>(R.id.btnNosPlus12).setOnClickListener { updateScoreNos(12) }
        view.findViewById<Button>(R.id.btnNosMinus1).setOnClickListener { updateScoreNos(-1) }
        view.findViewById<Button>(R.id.btnNosMinus3).setOnClickListener { updateScoreNos(-3) }

        // Eles buttons
        view.findViewById<Button>(R.id.btnElesPlus1).setOnClickListener { updateScoreEles(1) }
        view.findViewById<Button>(R.id.btnElesPlus3).setOnClickListener { updateScoreEles(3) }
        view.findViewById<Button>(R.id.btnElesPlus6).setOnClickListener { updateScoreEles(6) }
        view.findViewById<Button>(R.id.btnElesPlus9).setOnClickListener { updateScoreEles(9) }
        view.findViewById<Button>(R.id.btnElesPlus12).setOnClickListener { updateScoreEles(12) }
        view.findViewById<Button>(R.id.btnElesMinus1).setOnClickListener { updateScoreEles(-1) }
        view.findViewById<Button>(R.id.btnElesMinus3).setOnClickListener { updateScoreEles(-3) }

        view.findViewById<Button>(R.id.btnNewGame).setOnClickListener { resetGame() }

        return view
    }

    private fun updateScoreNos(value: Int) {
        scoreNos += value
        if (scoreNos < 0) scoreNos = 0
        if (scoreNos >= maxScore) {
            scoreNos = 0
            scoreEles = 0
            quedasNos++
            updateDots()
            if (quedasNos >= 2) {
                showWinner(tvLabelNos.text.toString())
            }
        }
        tvScoreNos.text = scoreNos.toString()
        tvScoreEles.text = scoreEles.toString()
    }

    private fun updateScoreEles(value: Int) {
        scoreEles += value
        if (scoreEles < 0) scoreEles = 0
        if (scoreEles >= maxScore) {
            scoreNos = 0
            scoreEles = 0
            quedasEles++
            updateDots()
            if (quedasEles >= 2) {
                showWinner(tvLabelEles.text.toString())
            }
        }
        tvScoreNos.text = scoreNos.toString()
        tvScoreEles.text = scoreEles.toString()
    }

    private fun updateDots() {
        dotNos1.alpha = if (quedasNos >= 1) 1.0f else 0.3f
        dotNos2.alpha = if (quedasNos >= 2) 1.0f else 0.3f
        dotEles1.alpha = if (quedasEles >= 1) 1.0f else 0.3f
        dotEles2.alpha = if (quedasEles >= 2) 1.0f else 0.3f
    }

    private fun showWinner(name: String) {
        val dateFormat = SimpleDateFormat("dd/MM, HH:mm", Locale.getDefault())
        val dateString = dateFormat.format(Date())

        val match = Match(
            team1Name = tvLabelNos.text.toString(),
            team2Name = tvLabelEles.text.toString(),
            team1Score = if (quedasNos >= 2) maxScore else scoreNos,
            team2Score = if (quedasEles >= 2) maxScore else scoreEles,
            date = dateString,
            team1Won = quedasNos >= 2
        )
        HistoryManager(requireContext()).saveMatch(match)

        val dialog = WinnerDialogFragment.newInstance(name)
        dialog.setListener(this)
        dialog.show(parentFragmentManager, "winner_dialog")
    }

    override fun onPlayAgain() {
        resetGame()
    }

    private fun resetGame() {
        scoreNos = 0
        scoreEles = 0
        quedasNos = 0
        quedasEles = 0
        tvScoreNos.text = "0"
        tvScoreEles.text = "0"
        updateDots()
    }
}