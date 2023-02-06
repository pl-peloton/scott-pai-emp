package com.example.android.testing.espresso.BasicSample

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.CoreMatchers.allOf

fun mainExample(func: () -> Any) = MainExample().apply { func() }

class MainExample {
    val textHeader = onView(withId(R.id.textToBeChanged))
    val textEditField = onView(withId(R.id.editTextUserInput))
    val changeTextButton = onView(withText(R.string.change_text))
    val openActivityButton = onView(withId(R.id.activityChangeTextBtn))
    val textShownButton = onView(allOf(withId(R.id.toggleButton)))

    fun typeTextIntoEditTextField(stringToInput: String) {
        textEditField.perform(click()).perform(typeText(stringToInput))
    }

    fun clickChangeTextButton() {
        changeTextButton.perform(click())
    }

    fun verifyTextHeaderString(expectedString: String) {
        textHeader.check(matches(withText(expectedString)))
    }
}