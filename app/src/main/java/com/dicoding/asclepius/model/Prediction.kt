package com.dicoding.asclepius.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "prediction_history")
data class Prediction(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val label: String,
    val score: Float,
    val imageUri: String,
    val timestamp: Long = System.currentTimeMillis()
)
