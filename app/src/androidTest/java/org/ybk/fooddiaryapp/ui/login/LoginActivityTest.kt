package org.ybk.fooddiaryapp.ui.login

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.firebase.auth.FirebaseAuth
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.ybk.fooddiaryapp.R

@RunWith(AndroidJUnit4::class)
class LoginActivityTest {

    @get:Rule
    val mActivityRule = ActivityScenarioRule(LoginActivity::class.java)

    @Before
    fun setUp() {

    }

    @Test
    fun adad()  {

    }
}