package com.example.trucogold

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment

class WinnerDialogFragment : DialogFragment() {

    interface WinnerDialogListener {
        fun onPlayAgain()
    }

    private var listener: WinnerDialogListener? = null
    private var winnerName: String = ""

    companion object {
        fun newInstance(winnerName: String): WinnerDialogFragment {
            val frag = WinnerDialogFragment()
            val args = Bundle()
            args.putString("winner_name", winnerName)
            frag.arguments = args
            return frag
        }
    }

    fun setListener(listener: WinnerDialogListener) {
        this.listener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return inflater.inflate(R.layout.dialog_winner, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        winnerName = arguments?.getString("winner_name") ?: ""
        view.findViewById<TextView>(R.id.tvWinnerTeam).text = winnerName

        view.findViewById<Button>(R.id.btnPlayAgain).setOnClickListener {
            listener?.onPlayAgain()
            dismiss()
        }
    }
}