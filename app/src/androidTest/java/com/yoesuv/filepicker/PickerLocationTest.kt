package com.yoesuv.filepicker

import android.content.Context
import android.os.SystemClock
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
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
import com.yoesuv.filepicker.utils.IdlingResource
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@RunWith(AndroidJUnit4::class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class PickerLocationTest {

    private lateinit var context: Context
    private val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun register() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
        IdlingRegistry.getInstance().register(IdlingResource.idlingresource)
    }

    @After
    fun unregister() {
        IdlingRegistry.getInstance().unregister(IdlingResource.idlingresource)
    }

    @Test
    fun pickLocationTest() {
        val instrumentation = InstrumentationRegistry.getInstrumentation()

        onView(withText(context.getString(R.string.button_location)))
            .check(matches(isDisplayed()))
        SystemClock.sleep(delay)
        onView(withId(R.id.btnMenuLocation)).perform(click())
        SystemClock.sleep(delay)

        onView(withId(R.id.buttonUserLocation)).perform(click())
        SystemClock.sleep(delay)
        val allowPermission = UiDevice.getInstance(instrumentation).findObject(
            UiSelector().text("While using the app")
        )
        if (allowPermission.exists()) {
            allowPermission.click()
            SystemClock.sleep(delay)
            onView(withId(R.id.tvUserLocation)).check(matches(not(withText(""))))
        }

        SystemClock.sleep(delay)
        device.pressBack()
    }

}