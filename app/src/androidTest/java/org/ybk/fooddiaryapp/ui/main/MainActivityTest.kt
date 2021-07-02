package org.ybk.fooddiaryapp.ui.main

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.runner.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.ybk.fooddiaryapp.R
import org.ybk.fooddiaryapp.ui.adddiary.AddDiaryActivity
import java.util.regex.Pattern.matches

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val intentsTestRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun should_appear_eaten_food_fragment_if_second_tap_is_clicked() {
        onView(withId(R.id.eatenFoodFragment)).perform(click())
    }

    @Test
    fun should_appear_share_food_fragment_if_second_tap_is_clicked() {
        onView(withId(R.id.shareFoodFragment)).perform(click())
    }

    @Test
    fun should_appear_food_map_fragment_if_second_tap_is_clicked() {
        onView(withId(R.id.foodMapFragment)).perform(click())
    }

    @Test
    fun should_appear_profile_fragment_if_second_tap_is_clicked() {
        onView(withId(R.id.profileFragment)).perform(click())
    }

    @Test
    fun should_appear_settings_fragment_if_second_tap_is_clicked() {
        onView(withId(R.id.settingsFragment)).perform(click())
    }
}