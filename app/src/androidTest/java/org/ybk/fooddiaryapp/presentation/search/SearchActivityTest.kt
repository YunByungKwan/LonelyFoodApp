package org.ybk.fooddiaryapp.presentation.search

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.hamcrest.CoreMatchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.ybk.fooddiaryapp.R

/**
 * <테스트 필수 사항>
 * - SearchView에 app:iconifiedByDefault="false" 속성 추가
 */

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
internal class SearchActivityTest {

    @get:Rule
    val mActivityRule = ActivityScenarioRule(SearchActivity::class.java)

    @Test
    fun test1() {
        val searchView = Espresso.onView(allOf(
            supportsInputMethods(),
            isDescendantOfA(withId(R.id.searchview))))

        val recyclerView = Espresso.onView(allOf(
            supportsInputMethods(),
            isDescendantOfA(withId(R.id.place_recycler_view))
        ))

        searchView.perform(ViewActions.typeText("beef"))
        searchView.perform(ViewActions.closeSoftKeyboard())
        Thread.sleep(1000)

        searchView.perform(ViewActions.clearText())
        Thread.sleep(1000)

        recyclerView.perform()
    }
}