package com.dicoding.asclepius.helper

import android.content.Context
import android.net.Uri
import com.dicoding.asclepius.Utils
import com.dicoding.asclepius.ml.CancerClassification
import org.tensorflow.lite.support.image.TensorImage


class ImageClassifierHelper(private val context: Context) {
    private lateinit var model: CancerClassification

    private fun setupImageClassifier() {
        // TODO: Menyiapkan Image Classifier untuk memproses gambar.
        model = CancerClassification.newInstance(context)
    }

    fun classifyStaticImage(imageUri: Uri): Pair<String, Float>? {
        return try {
            setupImageClassifier()

            val bitmap = Utils.uriToBitmap(context, imageUri)
            val tensorImage = TensorImage.fromBitmap(bitmap)

            val outputs = model.process(tensorImage)
            val probability = outputs.probabilityAsCategoryList.maxByOrNull { it.score }

            model.close()

            probability?.let {
                Pair(it.label, it.score)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}