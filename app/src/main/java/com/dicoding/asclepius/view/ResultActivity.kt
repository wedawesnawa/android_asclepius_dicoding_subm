package com.dicoding.asclepius.view

import android.gesture.Prediction
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dicoding.asclepius.R
import com.dicoding.asclepius.databinding.ActivityResultBinding
import com.dicoding.asclepius.model.PredictionDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.dicoding.asclepius.model.Prediction as AppPrediction

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private lateinit var database: PredictionDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = PredictionDatabase.getDatabase(this)
        displayResult()
    }

    private fun displayResult() {
        val label = intent.getStringExtra("RESULT_LABEL") ?: "Tidak ada hasil"
        val score = intent.getFloatExtra("RESULT_SCORE", 0f)
        val imageUri = intent.getStringExtra("RESULT_IMAGE_URI")?.let { Uri.parse(it) }
        binding.resultText.text = getString(R.string.result_text, label, score * 100)
        imageUri?.let {
            binding.resultImage.setImageURI(it)
        }
        savePrediction(label, score, imageUri.toString())
    }
    private fun savePrediction(label: String, score: Float, imageUri: String) {
        val prediction = AppPrediction(label = label, score = score, imageUri = imageUri) // Gunakan alias di sini

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                database.predictionDao().insertPrediction(prediction)
            }
        }
    }
}