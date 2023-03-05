package com.example.android.testing.espresso.RecyclerViewSample.tests

import android.content.Context
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.android.testing.espresso.RecyclerViewSample.MainActivity
import com.example.android.testing.espresso.RecyclerViewSample.R
import com.example.android.testing.espresso.RecyclerViewSample.robots.ActionBar.Companion.actionBar
import com.example.android.testing.espresso.RecyclerViewSample.robots.MainScreen
import com.example.android.testing.espresso.RecyclerViewSample.robots.MainScreen.Companion.mainScreen
import com.example.android.testing.espresso.RecyclerViewSample.robots.SettingsScreen.Companion.settingsScreen
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
//        1. Start app
//        2. Open settings menu
        actionBar {
            openSettingsScreen()
        }
//        3. Turn "Use negative item count" on
        settingsScreen {
            tapOnNegativeItemToggle()
        }
//        4. Go back to main menu
        Espresso.pressBack()
//        5. Verify elements now show "You have -{n} apples

        mainScreen {
            verifyTextInEachRowInRecycler("You have -X apples")
        }
//        6. Go back to settings menu
        actionBar {
            openSettingsScreen()
        }
//        7. Turn toggle off
        settingsScreen {
            tapOnNegativeItemToggle()
        }
//        8. Go back to main menu
        Espresso.pressBack()
//        9. Verify elements now display normally
        mainScreen {
            verifyTextInEachRowInRecycler("You have X apples")
        }
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
        actionBar {
            openSettingsScreen()
        }
        settingsScreen {
            verifyMiddleToggleLabelText(true)
            tapMiddleRowToggle()
            verifyMiddleToggleLabelText(false)
        }
    }

    @Test
    fun shouldDisplayDifferentCenterText() {
        /*
        Pai says no indices
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
        actionBar {
            openSettingsScreen()
        }
//        Middle toggle is on by default so not adding step to toggle
        settingsScreen {
            verifyMiddleToggleLabelText(true)
        }
        Espresso.pressBack()
        mainScreen {
            verifyMiddleRowText(true)
        }
        actionBar { openSettingsScreen() }
        settingsScreen {
            tapMiddleRowToggle()
            verifyMiddleToggleLabelText(false)
            Espresso.pressBack()
        }
        mainScreen {
            verifyMiddleRowText(false)
        }
    }
}
