package com.dicoding.asclepius.model

import com.dicoding.asclepius.model.Prediction as AppPrediction
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PredictionDao {
    @Insert
    suspend fun insertPrediction(prediction: AppPrediction)

    @Query("SELECT * FROM prediction_history ORDER BY timestamp DESC")
    fun getAllPredictions(): LiveData<List<AppPrediction>>
}