package com.example.android.testing.espresso.RecyclerViewSample.robots

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.android.testing.espresso.RecyclerViewSample.R
import com.example.android.testing.espresso.RecyclerViewSample.assertions.withRecyclerSize
import com.example.android.testing.espresso.RecyclerViewSample.getView
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

    fun verifyRowTextAt(index: Int, expectedString: String) {
        mainRecycler.scrollTo(index)
        mainRecycler.check(matches(withText(expectedString)))
    }

    fun verifyMainRecyclerElementsEqualTo(size: Int) {
        verifyNumberOfMainRecyclerElements(equalTo(size))
    }

    fun verifyMainRecyclerElementsGreaterThan(size: Int) {
        verifyNumberOfMainRecyclerElements(greaterThan(size))
    }

    fun getSizeOfARecyclerView(recyclerView: ViewInteraction = mainRecycler) =
        (recyclerView.getView() as RecyclerView).adapter!!.itemCount

    fun verifyTextInEachRowInRecycler() {
        val rowCount = getSizeOfARecyclerView()
//        while index <= rowCount
//        verifyRowTextAt

    }

    private fun verifyNumberOfMainRecyclerElements(matcher: Matcher<Int>) {
        mainRecycler.check(matches(withRecyclerSize(matcher)))
    }
}
