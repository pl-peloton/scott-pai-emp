package com.example.android.testing.espresso.RecyclerViewSample

import android.os.Bundle
import android.text.TextUtils.replace
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        supportFragmentManager.commit {
            replace(R.id.content, SettingsFragment())
        }
    }
}
