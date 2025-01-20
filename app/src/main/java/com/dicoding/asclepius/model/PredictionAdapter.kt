package com.dicoding.asclepius.model

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.asclepius.R
import com.dicoding.asclepius.databinding.ItemPredictionBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PredictionAdapter : ListAdapter<Prediction, PredictionAdapter.PredictionViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Prediction>() {
            override fun areItemsTheSame(oldItem: Prediction, newItem: Prediction): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Prediction, newItem: Prediction): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PredictionViewHolder {
        val binding = ItemPredictionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PredictionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PredictionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class PredictionViewHolder(private val binding: ItemPredictionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(prediction: Prediction) {
            binding.labelText.text = prediction.label
            binding.scoreText.text = binding.root.context.getString(R.string.confidence_score, prediction.score * 100)
            binding.timestampText.text = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault()).format(
                Date(prediction.timestamp)
            )
            Glide.with(binding.imageView.context)
                .load(Uri.parse(prediction.imageUri))
                .into(binding.imageView)
        }
    }
}
