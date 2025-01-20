package com.dicoding.asclepius.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.dicoding.asclepius.model.Prediction as AppPrediction
import com.dicoding.asclepius.model.PredictionDatabase

class PredictionViewModel(application: Application) : AndroidViewModel(application) {
    private val predictionDao = PredictionDatabase.getDatabase(application).predictionDao()
    val predictionList: LiveData<List<AppPrediction>> = predictionDao.getAllPredictions()
}