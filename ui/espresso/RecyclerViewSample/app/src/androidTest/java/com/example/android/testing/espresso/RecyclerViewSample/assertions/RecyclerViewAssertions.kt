package com.example.android.testing.espresso.RecyclerViewSample.assertions

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

fun withRecyclerSize(comparator: Matcher<Int>): Matcher<View> {
    return object : TypeSafeMatcher<View>() {
        override fun describeTo(description: Description) {
            description.appendText("checking recycler view has $comparator elements")
        }

        override fun matchesSafely(item: View): Boolean =
            if (item !is RecyclerView) {
                false
            } else {
                comparator.matches(item.adapter?.itemCount)
            }
    }
}
