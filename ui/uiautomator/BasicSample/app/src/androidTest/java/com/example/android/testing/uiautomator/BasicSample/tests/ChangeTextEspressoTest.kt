package com.example.android.testing.uiautomator.BasicSample.tests

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.android.testing.uiautomator.BasicSample.MainActivity
import com.example.android.testing.uiautomator.BasicSample.robots.ChangeTextModal
import com.example.android.testing.uiautomator.BasicSample.robots.ChangeTextModal.Companion.changeTextModal
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest

class ChangeTextEspressoTest {

//    private val context = ApplicationProvider.getApplicationContext<Context>()

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)
    @Before
    fun setup() {
    }
    @Test
    fun changeNameOfTest() {
        changeTextModal {
            verifyCurrentHeaderText("Hello UiAutomator!")
            enterTextIntoField("Scott wuz here")
            tapChangeTextButton()
            verifyCurrentHeaderText("Scott wuz here")
        }

    }
}