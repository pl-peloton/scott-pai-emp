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

import android.content.Context
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.PerformException
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Test class showcasing some [RecyclerViewActions] from Espresso.
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class RecyclerViewSampleTest {

    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)

    /**
     * Use [ActivityScenario] to create and launch the activity under test. This is a
     * replacement for [androidx.test.rule.ActivityTestRule].
     */
    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        sharedPrefs.edit {
            clear()
            putBoolean(context.resources.getString(R.string.settings_key_middle_row), true)
            commit()
        }
    }

    @Test
    fun clickOn35thElement() {
        // example showing generic actionOnItem()
        onView(withId(R.id.main_recycler))
            .perform(
                RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                    hasDescendant(withText("You have 35 apples")),
                    click()
                )
            )
    }

    @Test(expected = PerformException::class)
    fun itemWithText_doesNotExist() {
        // Attempt to scroll to an item that contains the special text.
        onView(withId(R.id.main_recycler)) // scrollTo will fail the test if no item matches.
            .perform(
                RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                    hasDescendant(withText("not in the list"))
                )
            )
    }

    @Test
    fun scrollToItemBelowFold_checkItsText() {
        // First scroll to the position that needs to be matched and click on it.
        onView(withId(R.id.main_recycler))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    ITEM_BELOW_THE_FOLD,
                    click()
                )
            )

        // Match the text in an item below the fold and check that it's displayed.
        val itemElementText = context
            .resources
            .getQuantityString(
                R.plurals.item_element_text,
                ITEM_BELOW_THE_FOLD,
                ITEM_BELOW_THE_FOLD
            )

        onView(withText(itemElementText))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun itemInMiddleOfList_hasSpecialText() {
        // First, scroll to the view holder using the isInTheMiddle matcher.
        onView(withId(R.id.main_recycler))
            .perform(
                RecyclerViewActions.scrollToHolder(
                    isInTheMiddle
                )
            )

        // Check that the item has the special text.
        val middleElementText =
            ApplicationProvider.getApplicationContext<Context>().resources.getString(R.string.middle)
        onView(withText(middleElementText))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    companion object {
        private const val ITEM_BELOW_THE_FOLD = 40

        /**
         * Matches the [CustomAdapter.ViewHolder]s in the middle of the list.
         */
        private val isInTheMiddle: Matcher<CustomAdapter.ViewHolder>
            private get() = object : TypeSafeMatcher<CustomAdapter.ViewHolder>() {
                override fun matchesSafely(customHolder: CustomAdapter.ViewHolder): Boolean {
                    return customHolder.isInTheMiddle
                }

                override fun describeTo(description: Description) {
                    description.appendText("item in the middle")
                }
            }
    }
}
