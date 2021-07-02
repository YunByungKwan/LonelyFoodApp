package org.ybk.fooddiaryapp.ui.diary

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.ybk.fooddiaryapp.R

@RunWith(AndroidJUnit4::class)
class DiaryFragmentTest {

    @get:Rule
    val intentRule = IntentsTestRule(FragmentScenario.EmptyFragmentActivity::class.java)

    @Before
    fun set_up() {
        launchFragmentInContainer<DiaryFragment>()
    }

    @Test
    fun should_send_intent_to_AddDiaryActivity_if_FloatingActionButton_is_clicked() {
        val targetAct = "org.ybk.fooddiaryapp.ui.adddiary.AddDiaryActivity"
        Thread.sleep(3000)
        onView(withId(R.id.lottieView)).perform(click())
        intended(IntentMatchers.hasComponent(targetAct))
    }

    @Test
    fun asd() {
//        val context = InstrumentationRegistry.getInstrumentation().targetContext
        Thread.sleep(3000)
        openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext())
//
        Thread.sleep(3000)
    }
}