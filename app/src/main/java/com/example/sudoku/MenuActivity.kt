package com.example.sudoku

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.sudoku.databinding.ActivityMenuBinding
import com.google.android.material.button.MaterialButton
import java.util.Locale

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding
    private lateinit var btnEasy: MaterialButton
    private lateinit var btnNormal: MaterialButton
    private lateinit var btnHard: MaterialButton
    private lateinit var spinnerLanguage: Spinner

    private var selectedDifficulty: Difficulty = Difficulty.NORMAL
    private val languages = arrayOf("Русский", "English")
    private val languageCodes = arrayOf("ru", "en")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        spinnerLanguage = binding.spinnerLanguage
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, languages)
        spinnerLanguage.adapter = adapter

        val currentIndex = languageCodes.indexOf(getCurrentLocale())
        if (currentIndex >= 0) {
            spinnerLanguage.setSelection(currentIndex, false)
        }

        spinnerLanguage.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedLanguageCode = languageCodes[position]
                setAppLocale(selectedLanguageCode)
                recreate()

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }


        btnEasy = binding.btnLevelEasy
        btnNormal = binding.btnLevelNormal
        btnHard = binding.btnLevelHard

        btnEasy.setOnClickListener { selectDifficulty(Difficulty.EASY) }
        btnNormal.setOnClickListener { selectDifficulty(Difficulty.NORMAL) }
        btnHard.setOnClickListener { selectDifficulty(Difficulty.HARD) }

        updateButtonColors()

        binding.btnPlay.setOnClickListener {
            val intent = Intent(this, PlayActivity::class.java)
            intent.putExtra("difficulty", selectedDifficulty.name)
            startActivity(intent)
        }
    }

    private fun getCurrentLocale(): String {
        return resources.configuration.locales.get(0).language
    }

    private fun setAppLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)

    }

    private fun selectDifficulty(difficulty: Difficulty) {
        selectedDifficulty = difficulty
        updateButtonColors()
    }

    private fun updateButtonColors() {
        val selectedColor = ContextCompat.getColor(this, R.color.green_light)
        val defaultColor = ContextCompat.getColor(this, R.color.gray)

        btnEasy.setBackgroundColor(if (selectedDifficulty == Difficulty.EASY) selectedColor else defaultColor)
        btnNormal.setBackgroundColor(if (selectedDifficulty == Difficulty.NORMAL) selectedColor else defaultColor)
        btnHard.setBackgroundColor(if (selectedDifficulty == Difficulty.HARD) selectedColor else defaultColor)
    }
}