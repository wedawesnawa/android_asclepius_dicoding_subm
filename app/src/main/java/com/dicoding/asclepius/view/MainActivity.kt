package com.dicoding.asclepius.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.asclepius.databinding.ActivityMainBinding
import com.dicoding.asclepius.helper.ImageClassifierHelper
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var currentImageUri: Uri? = null
    private lateinit var classifierHelper: ImageClassifierHelper
    private lateinit var mainViewModel: MainViewModel

    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            mainViewModel.setImageUri(uri)
            if (uri != null) {
                currentImageUri = uri
                showImage()
            } else {
                showToast("Gagal mengambil gambar.")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        classifierHelper = ImageClassifierHelper(this)

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        mainViewModel.currentImageUri.observe(this, Observer { uri ->
            binding.previewImageView.setImageURI(uri)
        })

        binding.galleryButton.setOnClickListener {
            startGallery()
        }

        binding.analyzeButton.setOnClickListener {
            analyzeImage()
        }
        binding.historyButton.setOnClickListener {
            moveToHistory()
        }
        binding.newsButton.setOnClickListener {
            moveToNews()
        }
    }

    private fun moveToNews() {
        val intent = Intent(this, NewsActivity::class.java)
        startActivity(intent)
    }

    private fun startGallery() {
        galleryLauncher.launch("image/*")
    }

    private fun showImage() {
        currentImageUri?.let {
            binding.previewImageView.setImageURI(it)
        } ?: showToast("Tidak ada gambar yang dipilih.")
    }

    private fun analyzeImage() {
        mainViewModel.currentImageUri.value?.let { uri ->
            val result = classifierHelper.classifyStaticImage(uri)
            if (result != null) {
                val (label, score) = result
                moveToResult(label, score)
            } else {
                showToast("Terjadi kesalahan saat menganalisis gambar.")
            }
        } ?: showToast("Pilih gambar terlebih dahulu.")
    }

    private fun moveToResult(label: String, score: Float) {
        val intent = Intent(this, ResultActivity::class.java).apply {
            putExtra("RESULT_LABEL", label)
            putExtra("RESULT_SCORE", score)
            putExtra("RESULT_IMAGE_URI", mainViewModel.currentImageUri.value.toString())
        }
        startActivity(intent)
    }
    private fun moveToHistory() {
        val intent = Intent(this, HistoryActivity::class.java)
        startActivity(intent)
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}