package com.example.android.testing.uiautomator.BasicSample.robots

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.android.testing.uiautomator.BasicSample.R

class ChangeTextModal {
    companion object {
        fun changeTextModal(func: ChangeTextModal.() -> Any) = ChangeTextModal().apply { func() }
    }

    private val headerTextToBeChanged = onView(withId(R.id.textToBeChanged))
    private val textInputField = onView(withId(R.id.editTextUserInput))
    private val changeTextButton = onView(withId(R.id.changeTextBt))
    private val openActivityAndChangeTextBtn = onView(withId(R.id.activityChangeTextBtn))
    private val newNameString = "Scott wuz here"

    fun enterTextIntoField(textString: String) {
        textInputField.perform(typeText(textString))
    }

    fun verifyCurrentHeaderText(expectedString: String) {
        headerTextToBeChanged.check(matches(withText(expectedString)))
    }

    fun tapChangeTextButton() {
        changeTextButton.perform(click())
    }

}