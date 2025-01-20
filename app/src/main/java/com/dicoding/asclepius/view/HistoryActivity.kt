package com.dicoding.asclepius.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.asclepius.R
import com.dicoding.asclepius.model.PredictionAdapter
import com.dicoding.asclepius.view.PredictionViewModel

class HistoryActivity : AppCompatActivity() {

    private lateinit var adapter: PredictionAdapter
    private lateinit var viewModel: PredictionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        adapter = PredictionAdapter()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        viewModel = ViewModelProvider(this).get(PredictionViewModel::class.java)
        loadHistory()
    }

    private fun loadHistory() {
        viewModel.predictionList.observe(this, Observer { predictions ->
            predictions?.let {
                adapter.submitList(it)
            }
        })
    }
}
