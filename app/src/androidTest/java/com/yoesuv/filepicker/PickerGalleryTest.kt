package com.yoesuv.filepicker

import android.content.Context
import android.os.SystemClock
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import com.yoesuv.filepicker.menu.main.views.MainActivity
import org.junit.Assert.*
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class PickerGalleryTest {

    private val delay = 1000L
    private lateinit var context: Context

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)
//    @get:Rule
//    var galleryRule = GrantPermissionRule.grant(android.Manifest.permission.READ_EXTERNAL_STORAGE)
//    @get:Rule
//    var galleryRule33 = GrantPermissionRule.grant(android.Manifest.permission.READ_MEDIA_IMAGES)

    @Before
    fun register() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @Test
    fun pickFromGallery() {
        val instrumentation = InstrumentationRegistry.getInstrumentation()

        onView(ViewMatchers.withText(context.getString(R.string.button_gallery)))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        SystemClock.sleep(delay)
        onView(withId(R.id.btnMenuGallery)).perform(click())
        SystemClock.sleep(delay)

        onView(withId(R.id.buttonOpenGallery)).perform(click())
        SystemClock.sleep(delay)
        SystemClock.sleep(delay)
        val allowPermission = UiDevice.getInstance(instrumentation).findObject(
            UiSelector().clickable(true).checkable(false).index(1)
        )
        if (allowPermission.exists()) {
            allowPermission.click()
        }
    }
}