package com.example.android.testing.espresso.RecyclerViewSample

import android.content.Context
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class ToggleTest {

    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)

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
    fun shouldShowNegativeNumbersWhenEnabled() {
        /*
        1. Start app
        2. Open settings menu
        3. Turn "Use negative item count" on
        4. Go back to main menu
        5. Verify elements now show "You have -{n} apples
        6. Go back to settings menu
        7. Turn toggle off
        8. Go back to main menu
        9. Verify elements now display normally
         */
    }

    @Test
    fun toggleTextShouldUpdate() {
        /*
        1. Start app
        2. Open settings menu
        3. Turn "Toggle middle row text" on
        4. Verify subtext is "Displays a different string for the center row"
        5. Turn toggle off
        6. Verify subtext is "Displays the recycler view dataset normally"
         */
    }

    @Test
    fun shouldDisplayDifferentCenterText() {
        /*
        1. Start app
        2. Open settings menu
        3. Turn "Toggle middle row text" on
        4. Go back to main menu
        5. Verify center element says "This is the middle!"
        6. Go back to settings menu
        7. Turn toggle off
        8. Go back to main menu
        9. Verify center element says "You have {n} apples"
         */
    }
}
