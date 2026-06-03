package com.example.trucogold

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HistoricFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_historic, container, false)
        
        val rvHistory = view.findViewById<RecyclerView>(R.id.rvHistory)
        val tvEmpty = view.findViewById<TextView>(R.id.tvEmpty)
        
        val matches = HistoryManager(requireContext()).getMatches()
        
        if (matches.isEmpty()) {
            tvEmpty.visibility = View.VISIBLE
            rvHistory.visibility = View.GONE
        } else {
            tvEmpty.visibility = View.GONE
            rvHistory.visibility = View.VISIBLE
            rvHistory.layoutManager = LinearLayoutManager(requireContext())
            rvHistory.adapter = MatchAdapter(matches)
        }
        
        return view
    }
}