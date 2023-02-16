package com.example.android.testing.espresso.RecyclerViewSample.robots

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.android.testing.espresso.RecyclerViewSample.R
import com.example.android.testing.espresso.RecyclerViewSample.tests.RecyclerViewSampleTest
import org.hamcrest.CoreMatchers.allOf

class SettingsScreen {

    companion object {
        fun settingsScreen(func: SettingsScreen.() -> Any) = SettingsScreen().apply { func() }
    }

    /*
    Hint for toggles - because each toggle is the same, we need to match against neighboring views

    Settingsoi8
        Row 1
            Label - Toggle
        Row 2
            Label - Toggle
    So we want to find the row 1 toggle whose sibling in the hierarchy is Row 1 Label
     */
    private val negativeItemLabel: ViewInteraction = onView(withText(R.string.settings_toggle_negative_items))
    private val negativeItemToggle: ViewInteraction = onView(allOf(
        withId(R.id.switchWidget),
        hasSibling(withText(R.string.settings_toggle_negative_items))
    ))

    private val middleRowLabel: ViewInteraction = onView(withText(R.string.middle))
    private val middleRowToggle: ViewInteraction = onView(
        allOf(
            withId(R.id.switchWidget),
            hasSibling(withText(R.string.middle))
        )
    )

    fun tapOnNegativeItemToggle() {
        negativeItemLabel.check(matches(isDisplayed()))
        negativeItemToggle.perform(click())
    }

    fun tapMiddleRowToggle() {
        middleRowLabel.check(matches(isDisplayed()))
        middleRowToggle.perform(click())
    }

    init {
        // we can also use constructors / init blocks to do things each time SettingsScreen() is invoked
        negativeItemLabel.check(matches(isDisplayed()))
    }
}
