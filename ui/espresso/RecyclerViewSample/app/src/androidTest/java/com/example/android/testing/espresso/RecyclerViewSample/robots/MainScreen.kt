package com.example.android.testing.espresso.RecyclerViewSample.robots

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.android.testing.espresso.RecyclerViewSample.R
import com.example.android.testing.espresso.RecyclerViewSample.scrollTo

class MainScreen {
    val mainRecycler = onView(withId(R.id.main_recycler))
    val middleElementText =
        ApplicationProvider.getApplicationContext<Context>().resources.getString(R.string.middle)

    fun scrollTo(stringToSearch: String) {
        mainRecycler.scrollTo(withText(stringToSearch))
    }
}