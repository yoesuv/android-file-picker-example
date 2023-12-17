package com.yoesuv.filepicker

import android.content.Context
import android.os.SystemClock
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import com.yoesuv.filepicker.TestData.delay
import com.yoesuv.filepicker.menu.main.views.MainActivity
import com.yoesuv.filepicker.utils.isTiramisu
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

    private lateinit var context: Context
    private val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun register() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @Test
    fun pickFromGallery() {
        val instrumentation = InstrumentationRegistry.getInstrumentation()

        onView(withText(context.getString(R.string.button_gallery)))
            .check(matches(isDisplayed()))
        SystemClock.sleep(delay)
        onView(withId(R.id.btnMenuGallery)).perform(click())
        SystemClock.sleep(delay)

        onView(withId(R.id.buttonOpenGallery)).perform(click())
        SystemClock.sleep(delay)
        SystemClock.sleep(delay)
        if (!isTiramisu()) {
            val allowPermission = UiDevice.getInstance(instrumentation).findObject(
                UiSelector().text("Allow")
            )
            if (allowPermission.exists()) {
                allowPermission.click()
                SystemClock.sleep(delay)
                // open gallery
                val gallery = UiDevice.getInstance(instrumentation).findObject(
                    UiSelector().text("Gallery")
                )
                val download = UiDevice.getInstance(instrumentation).findObject(
                    UiSelector().text("Download")
                )
                if (gallery.exists()) {
                    gallery.click()
                    SystemClock.sleep(delay)
                    val always = UiDevice.getInstance(instrumentation).findObject(
                        UiSelector().text("Always")
                    )
                    if (always.exists()) {
                        always.click()
                        SystemClock.sleep(delay)
                        device.pressBack()
                    }
                } else if (download.exists()) {
                    if (download.exists()) {
                        download.click()
                        SystemClock.sleep(delay)
                    }
                }
                SystemClock.sleep(delay)
                device.pressBack()
            }
        }
        SystemClock.sleep(delay)
        device.pressBack()
        SystemClock.sleep(delay)
    }
}