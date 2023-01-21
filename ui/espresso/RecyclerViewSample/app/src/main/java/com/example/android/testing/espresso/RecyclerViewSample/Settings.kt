package com.example.android.testing.espresso.RecyclerViewSample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Settings : AppCompatActivity() {

    private val recyclerView = findViewById<RecyclerView>(R.id.settings_recycler)
    private val settings = listOf(
        R.string.settings_toggle_negative_items,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        val dataSet = mutableListOf<String>()
    }


}
