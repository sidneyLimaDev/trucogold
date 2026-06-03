package com.example.trucogold

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.switchmaterial.SwitchMaterial
import android.widget.RadioGroup

class ConfigurationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_configuration, container, false)
        val prefs = requireActivity().getSharedPreferences("truco_prefs", Context.MODE_PRIVATE)

        // Tema
        val rgTheme = view.findViewById<RadioGroup>(R.id.rgTheme)
        val savedTheme = prefs.getInt("theme", AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        when (savedTheme) {
            AppCompatDelegate.MODE_NIGHT_NO -> rgTheme.check(R.id.rbThemeLight)
            AppCompatDelegate.MODE_NIGHT_YES -> rgTheme.check(R.id.rbThemeDark)
            else -> rgTheme.check(R.id.rbThemeSystem)
        }

        rgTheme.setOnCheckedChangeListener { _, checkedId ->
            val mode = when (checkedId) {
                R.id.rbThemeLight -> AppCompatDelegate.MODE_NIGHT_NO
                R.id.rbThemeDark -> AppCompatDelegate.MODE_NIGHT_YES
                else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            }
            AppCompatDelegate.setDefaultNightMode(mode)
            prefs.edit().putInt("theme", mode).apply()
        }

        // Tela ligada
        val swKeepOn = view.findViewById<SwitchMaterial>(R.id.switchKeepScreenOn)
        swKeepOn.isChecked = prefs.getBoolean("keep_screen_on", false)
        swKeepOn.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("keep_screen_on", isChecked).apply()
        }

        // Pontuação máxima
        val toggleScore = view.findViewById<MaterialButtonToggleGroup>(R.id.toggleScore)
        val maxScore = prefs.getInt("max_score", 12)
        toggleScore.check(if (maxScore == 15) R.id.btnScore15 else R.id.btnScore12)
        toggleScore.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                val score = if (checkedId == R.id.btnScore15) 15 else 12
                prefs.edit().putInt("max_score", score).apply()
            }
        }

        // Nomes dos Times
        val etTeam1 = view.findViewById<EditText>(R.id.etTeam1)
        val etTeam2 = view.findViewById<EditText>(R.id.etTeam2)

        etTeam1.setText(prefs.getString("team1_name", ""))
        etTeam2.setText(prefs.getString("team2_name", ""))

        etTeam1.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                prefs.edit().putString("team1_name", s.toString()).apply()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        etTeam2.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                prefs.edit().putString("team2_name", s.toString()).apply()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        return view
    }
}