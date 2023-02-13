package com.example.android.testing.espresso.RecyclerViewSample.robots

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.android.testing.espresso.RecyclerViewSample.R

class SettingsScreen {

    companion object {
        fun settingsScreen(func: SettingsScreen.() -> Any) = SettingsScreen().apply { func() }
    }

    /*
    Hint for toggles - because each toggle is the same, we need to match against neighboring views

    Settings
        Row 1
            Label - Toggle
        Row 2
            Label - Toggle
    So we want to find the row 1 toggle whose sibling in the hierarchy is Row 1 Label
     */

    private val negativeItemLabel: ViewInteraction = onView(withText(R.string.settings_toggle_negative_items))
    private val negativeItemToggle: ViewInteraction = TODO("Not yet implemented")

    private val middleRowLabel: ViewInteraction = TODO("Not yet implemented")
    private val middleRowToggle: ViewInteraction = TODO("Not yet implemented")

    init {
        // we can also use constructors / init blocks to do things each time SettingsScreen() is invoked
        negativeItemLabel.check(matches(isDisplayed()))
    }
}
