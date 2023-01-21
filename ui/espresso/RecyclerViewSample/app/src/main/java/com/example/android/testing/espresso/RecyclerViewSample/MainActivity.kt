/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.testing.espresso.RecyclerViewSample

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Shows a list using a RecyclerView.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadRecycler()
    }

    override fun onResume() {
        Log.d("Main", "onResume")
        super.onResume()
        loadRecycler()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.action_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    fun loadRecycler() {
        // Create a RecyclerView, a LayoutManager, a data Adapter and wire everything up.
        val recyclerView = findViewById<View>(R.id.main_recycler) as RecyclerView
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        val dataSet: MutableList<String> = ArrayList(DATASET_COUNT)

        val negativeCount = PreferenceManager
            .getDefaultSharedPreferences(this)
            .getBoolean("settings_negative_items", false)

        val mainRecyclerItemText = getString(R.string.item_element_text) + if (negativeCount) " -" else ""

        for (i in 0 until DATASET_COUNT) {
            dataSet.add("$mainRecyclerItemText$i")
        }
        val adapter = CustomAdapter(dataSet, applicationContext)
        recyclerView.adapter = adapter
    }

    companion object {
        private const val DATASET_COUNT = 50
    }
}
