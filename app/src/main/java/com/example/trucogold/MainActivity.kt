package com.example.trucogold

import android.content.res.ColorStateList
import android.graphics.Typeface
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        
        // Initial fragment
        if (savedInstanceState == null) {
            replaceFragment(HomeFragment())
            updateNavStyles(R.id.btnHome)
        }

        // Setup manual navbar clicks
        findViewById<View>(R.id.btnHome).setOnClickListener {
            replaceFragment(HomeFragment())
            updateNavStyles(R.id.btnHome)
        }

        findViewById<View>(R.id.btnHistoric).setOnClickListener {
            replaceFragment(HistoricFragment())
            updateNavStyles(R.id.btnHistoric)
        }

        findViewById<View>(R.id.btnRules).setOnClickListener {
            replaceFragment(RulesFragment())
            updateNavStyles(R.id.btnRules)
        }

        // Link settings gear icon to ConfigurationFragment
        findViewById<ImageButton>(R.id.ConfigurationFragment).setOnClickListener {
            replaceFragment(ConfigurationFragment())
            clearNavStyles()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun updateNavStyles(selectedId: Int) {
        clearNavStyles()

        val selectedIconColor = getThemedColor(R.attr.navSelectedIconColor)
        val selectedTextColor = getThemedColor(android.R.attr.colorPrimary)

        when (selectedId) {
            R.id.btnHome -> {
                findViewById<FrameLayout>(R.id.bgHome).setBackgroundResource(R.drawable.ic_nav_active_bg)
                findViewById<ImageView>(R.id.imgHome).imageTintList = ColorStateList.valueOf(selectedIconColor)
                findViewById<TextView>(R.id.txtHome).apply {
                    setTypeface(null, Typeface.BOLD)
                    setTextColor(selectedTextColor)
                }
            }
            R.id.btnHistoric -> {
                findViewById<FrameLayout>(R.id.bgHistoric).setBackgroundResource(R.drawable.ic_nav_active_bg)
                findViewById<ImageView>(R.id.imgHistoric).imageTintList = ColorStateList.valueOf(selectedIconColor)
                findViewById<TextView>(R.id.txtHistoric).apply {
                    setTypeface(null, Typeface.BOLD)
                    setTextColor(selectedTextColor)
                }
            }
            R.id.btnRules -> {
                findViewById<FrameLayout>(R.id.bgRules).setBackgroundResource(R.drawable.ic_nav_active_bg)
                findViewById<ImageView>(R.id.imgRules).imageTintList = ColorStateList.valueOf(selectedIconColor)
                findViewById<TextView>(R.id.txtRules).apply {
                    setTypeface(null, Typeface.BOLD)
                    setTextColor(selectedTextColor)
                }
            }
        }
    }

    private fun clearNavStyles() {
        val defaultColor = getThemedColor(android.R.attr.colorPrimary)
        
        findViewById<FrameLayout>(R.id.bgHome).background = null
        findViewById<FrameLayout>(R.id.bgHistoric).background = null
        findViewById<FrameLayout>(R.id.bgRules).background = null

        findViewById<ImageView>(R.id.imgHome).imageTintList = ColorStateList.valueOf(defaultColor)
        findViewById<ImageView>(R.id.imgHistoric).imageTintList = ColorStateList.valueOf(defaultColor)
        findViewById<ImageView>(R.id.imgRules).imageTintList = ColorStateList.valueOf(defaultColor)

        findViewById<TextView>(R.id.txtHome).apply {
            setTypeface(null, Typeface.NORMAL)
            setTextColor(defaultColor)
        }
        findViewById<TextView>(R.id.txtHistoric).apply {
            setTypeface(null, Typeface.NORMAL)
            setTextColor(defaultColor)
        }
        findViewById<TextView>(R.id.txtRules).apply {
            setTypeface(null, Typeface.NORMAL)
            setTextColor(defaultColor)
        }
    }

    @ColorInt
    private fun getThemedColor(@AttrRes attr: Int): Int {
        val typedValue = TypedValue()
        theme.resolveAttribute(attr, typedValue, true)
        return typedValue.data
    }
}