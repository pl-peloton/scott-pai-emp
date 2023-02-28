package com.example.android.testing.espresso.RecyclerViewSample

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItem
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToHolder
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

fun ViewInteraction.scrollTo(position: Int): ViewInteraction =
    perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(position))

interface ViewHolderInteraction {
    val matcher: Matcher<RecyclerView.ViewHolder>

    fun perform(vararg actions: ViewAction): ViewInteraction
}

fun ViewInteraction.onViewHolder(matcher: Matcher<RecyclerView.ViewHolder>): ViewHolderInteraction =
    object : ViewHolderInteraction {
        override val matcher: Matcher<RecyclerView.ViewHolder> = matcher

        override fun perform(vararg actions: ViewAction): ViewInteraction =
            this@onViewHolder.perform(*actions)
    }

fun ViewHolderInteraction.scrollTo(): ViewInteraction =
    perform(RecyclerViewActions.scrollToHolder(matcher))

fun ViewInteraction.scrollTo(matcher: Matcher<View>): ViewInteraction =
    perform(scrollToHolder(viewHolderMatcher(hasDescendant(matcher))))

fun ViewHolderInteraction.click(): ViewInteraction =
    perform(RecyclerViewActions.actionOnHolderItem(matcher, ViewActions.click()))

fun ViewInteraction.tapRecyclerItemAtPosition(position: Int): ViewInteraction =
    scrollTo(position).perform(
        RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
            position,
            ViewActions.click()
        )
    )

fun ViewInteraction.longTapRecyclerItemAtPosition(position: Int): ViewInteraction =
    scrollTo(position).perform(
        actionOnItemAtPosition<RecyclerView.ViewHolder>(
            position,
            ViewActions.longClick()
        )
    )

fun ViewInteraction.doubleTapRecyclerItemAtPosition(position: Int): ViewInteraction =
    scrollTo(position).perform(
        RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
            position,
            ViewActions.doubleClick()
        )
    )

fun ViewInteraction.doubleTapRecyclerItemWithText(text: String): ViewInteraction =
    perform(
        actionOnItem<RecyclerView.ViewHolder>
        (hasDescendant(withText(text)), scrollTo())
    )
        .perform(ViewActions.doubleClick())

fun ViewInteraction.tapRecyclerItemWithText(text: String): ViewInteraction =
    perform(
        actionOnItem<RecyclerView.ViewHolder>(hasDescendant(withText(text)), scrollTo())
    )
        .perform(
            actionOnItem<RecyclerView.ViewHolder>
            (hasDescendant(withText(text)), ViewActions.click())
        )

fun ViewInteraction.longTapRecyclerItemWithText(text: String): ViewInteraction =
    perform(
        actionOnItem<RecyclerView.ViewHolder>(hasDescendant(withText(text)), scrollTo())
    )
        .perform(
            actionOnItem<RecyclerView.ViewHolder>
            (hasDescendant(withText(text)), ViewActions.longClick())
        )

fun ViewInteraction.tapRecyclerItemWithTextAndId(text: String, resId: Int): ViewInteraction {
    val itemMatcher =
        hasDescendant(
            allOf(
                withText(text),
                withId(resId)
            )
        )
    return this.perform(actionOnItem<RecyclerView.ViewHolder>(itemMatcher, scrollTo()))
        .perform(actionOnItem<RecyclerView.ViewHolder>(itemMatcher, ViewActions.click()))
}

/**
 * Asserts the recycler item at a position has given matcher.
 */
fun ViewInteraction.itemAtPosition(position: Int, itemMatcher: Matcher<View?>): ViewInteraction {
    return this.check(
        ViewAssertions.matches(
            object : BoundedMatcher<View?, RecyclerView>(RecyclerView::class.java) {
                override fun describeTo(description: Description) {
                    description.appendText("has item at position $position: ")
                    itemMatcher.describeTo(description)
                }
                override fun matchesSafely(view: RecyclerView): Boolean {
                    val viewHolder = view.findViewHolderForAdapterPosition(position) ?: return false
                    return itemMatcher.matches(viewHolder.itemView)
                }
            }
        )
    )
}

/**
 * Intended for scenarios where a recyclerView item might be larger than the screen,
 * and so even after scrolling to the item, parts of it may not be visible/clickable.  [holderMatcher]
 * identifies which recyclerView item serves as the parent.  This method
 * will call performClick() on [holderMatcher]'s child view with id [childResourceId],
 * and performClick() does not care whether or not it's view is on the screen.
 */
fun ViewInteraction.clickOnHolderChild(holderMatcher: Matcher<View>, childResourceId: Int) {
    this.perform(
        RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
            holderMatcher,
            object : ViewAction {
                override fun getConstraints() = null

                override fun getDescription() = "click child view with id $childResourceId"

                override fun perform(uiController: UiController, view: View) {
                    val v = view.findViewById<View>(childResourceId)
                    v.performClick()
                }
            }
        )
    )
}

/**
 * get View from ViewInteraction
 *
 * this is an extension function
 */
fun ViewInteraction.getView(): View {
    var viewToReturn: View? = null
    this.perform(
        object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return ViewMatchers.isAssignableFrom(View::class.java)
            }

            override fun getDescription(): String {
                return "Get View from ViewInteraction"
            }

            override fun perform(uiController: UiController, view: View) {
                viewToReturn = view
            }
        })
    return viewToReturn!!
}

/**
 * Creates matcher for view holder with given item view matcher.
 *
 * @param itemViewMatcher a item view matcher which is used to match item.
 * @return a matcher which matches a view holder containing item matching itemViewMatcher.
 */
fun viewHolderMatcher(itemViewMatcher: Matcher<View>): Matcher<RecyclerView.ViewHolder> {
    return object : TypeSafeMatcher<RecyclerView.ViewHolder>() {

        override fun matchesSafely(viewHolder: RecyclerView.ViewHolder): Boolean {
            return itemViewMatcher.matches(viewHolder.itemView)
        }

        override fun describeTo(description: Description) {
            description.appendText("holder with view: ")
            itemViewMatcher.describeTo(description)
        }
    }
}
