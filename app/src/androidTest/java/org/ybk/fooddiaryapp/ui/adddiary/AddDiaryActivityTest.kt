package org.ybk.fooddiaryapp.ui.adddiary

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.ybk.fooddiaryapp.R

@RunWith(AndroidJUnit4::class)
class AddDiaryActivityTest {

    @get:Rule
    val intentsTestRule = IntentsTestRule(AddDiaryActivity::class.java)

    @Test
    fun should_send_camera_intent_if_camera_icon_is_clicked() {
        onView(withId(R.id.camera_btn)).perform(click())
        intended(IntentMatchers.hasAction("android.media.action.IMAGE_CAPTURE"))
        intended(IntentMatchers.toPackage("com.sec.android.app.camera"))
    }

    @Test
    fun should_send_chooser_intent_if_gallery_icon_is_clicked() {
        onView(withId(R.id.gallery_btn)).perform(click())
        intended(IntentMatchers.hasAction("android.intent.action.CHOOSER"))
        intended(IntentMatchers.toPackage("android, com.android.systemui"))
    }
}