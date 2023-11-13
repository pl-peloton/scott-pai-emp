package com.example.android.testing.espresso.RecyclerViewSample.robots

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.android.testing.espresso.RecyclerViewSample.R
import com.example.android.testing.espresso.RecyclerViewSample.assertions.withRecyclerSize
import com.example.android.testing.espresso.RecyclerViewSample.getView
import com.example.android.testing.espresso.RecyclerViewSample.itemAtPosition
import com.example.android.testing.espresso.RecyclerViewSample.scrollTo
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.Matcher
import org.hamcrest.Matchers.greaterThan

class MainScreen {

    companion object {
        fun mainScreen(func: MainScreen.() -> Any) = MainScreen().apply { func() }
    }

    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val mainRecycler = onView(withId(R.id.main_recycler))
    val middleElementText = context.resources.getString(R.string.middle)

    init {
        // we can also use constructors to do stuff each time MainScreen() is invoked
        mainRecycler.check(matches(isDisplayed()))
    }

    fun scrollTo(stringToSearch: String) {
        mainRecycler.scrollTo(withText(stringToSearch))
    }

    /**
     * Checks the string of a specific row
     */
    fun verifyRowTextAt(index: Int, expectedString: String) {
        mainRecycler.perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(index))
        mainRecycler.itemAtPosition(index, hasDescendant(withText(expectedString)))
//        mainRecycler.check(matches(withText(expectedString)))
    }

    fun verifyMainRecyclerElementsEqualTo(size: Int) {
        verifyNumberOfMainRecyclerElements(equalTo(size))
    }

    fun verifyMainRecyclerElementsGreaterThan(size: Int) {
        verifyNumberOfMainRecyclerElements(greaterThan(size))
    }

    fun getSizeOfARecyclerView(recyclerView: ViewInteraction = mainRecycler) =
        (recyclerView.getView() as RecyclerView).adapter!!.itemCount

    fun verifyTextInEachRowInRecycler(defaultPluralString: String) {
//        String provided needs X in place of number
        val singularString: String = defaultPluralString.replace("s","")
        val rowCount = getSizeOfARecyclerView()
        val middleRowIndex = (rowCount / 2)
        println("The recycler view was found to have $rowCount rows.")
        var index = 0
        while (rowCount > index) {
            if (index == 1) {
//                var
                var expectedString = singularString.replace("X", "$index")
                verifyRowTextAt(index, expectedString)
            } else if (index == middleRowIndex) {
                verifyRowTextAt(index, middleElementText)
            } else {
                var expectedString = defaultPluralString.replace("X", "$index")
                verifyRowTextAt(index, expectedString)
            }
            index += 1
        }
    }

    fun verifyMiddleRowText(textIsShowing: Boolean) {
        val rowCount = getSizeOfARecyclerView()
        val middleRowIndex = (rowCount / 2)

        if (textIsShowing) {
            mainRecycler.perform(
                RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(middleRowIndex)
            ).check(matches(hasDescendant(withText(middleElementText))))
        } else if (textIsShowing == false) {
            mainRecycler.perform(
                RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(middleRowIndex)
            ).check(matches(hasDescendant(withText("You have $middleRowIndex apples"))))
        }

    }

    private fun verifyNumberOfMainRecyclerElements(matcher: Matcher<Int>) {
        mainRecycler.check(matches(withRecyclerSize(matcher)))
    }
}
