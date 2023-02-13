package com.example.android.testing.espresso.RecyclerViewSample.robots

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.platform.app.InstrumentationRegistry
import com.example.android.testing.espresso.RecyclerViewSample.R

class ActionBar {

    companion object {
        fun actionBar(func: ActionBar.() -> Any) = ActionBar().apply { func() }
    }

    // in addition to ApplicationProvider, we can also get context via InstrumentationRegistry
    // context = context of our test app, or test apk
    // targetContext = context of app under test, or app apk
    private val testContext = InstrumentationRegistry.getInstrumentation().context
    private val targetContext = InstrumentationRegistry.getInstrumentation().targetContext

    private val settings = onView(withText(R.string.settings))

    private fun openActionBarOverflowMenu() {
        openActionBarOverflowOrOptionsMenu(targetContext)
    }

    fun openSettingsScreen() {
        openActionBarOverflowMenu()
        settings.perform(click())
    }
}
