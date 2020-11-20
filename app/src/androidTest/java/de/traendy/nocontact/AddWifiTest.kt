package de.traendy.nocontact

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.google.android.material.textfield.TextInputLayout
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)
class AddWifiTest {

    @Rule
    @JvmField
    val mActivityTestRule = ActivityScenarioRule(MainActivity::class.java)

    @Rule
    @JvmField
    val activityAndScreenshotRule: TestRule = RuleChain
            .outerRule(mActivityTestRule)
            .around(ScreenshotTestWatcher())


    @Test
    fun addWifiWpaTest() {
        val targetContext: Context = ApplicationProvider.getApplicationContext()
        val floatingActionButton = onView(
                allOf(withId(R.id.addButton), withContentDescription("Add QR Code."),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(`is`("androidx.coordinatorlayout.widget.CoordinatorLayout")),
                                        2),
                                0),
                        isDisplayed()))
        floatingActionButton.perform(click())

        val floatingActionButton2 = onView(
                allOf(withId(R.id.wlanButton), withContentDescription("Add QR Code."),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(`is`("androidx.coordinatorlayout.widget.CoordinatorLayout")),
                                        2),
                                5),
                        isDisplayed()))
        floatingActionButton2.perform(click())

        val materialButton = onView(
                allOf(withId(R.id.createQrCodeButton), withText("Create QR Code"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment),
                                        0),
                                2),
                        isDisplayed()))
        materialButton.perform(click())

        // assert error view
        val authTypeErrorView = onView(withId(R.id.authTypeError)).check(matches(isDisplayed()))

        val materialRadioButton = onView(
                allOf(withId(R.id.wpa), withText("WPA"),
                        childAtPosition(
                                allOf(withId(R.id.authTypeGroup),
                                        childAtPosition(
                                                withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                                                2)),
                                1)))
        materialRadioButton.perform(scrollTo(), click())

        val materialButton2 = onView(
                allOf(withId(R.id.createQrCodeButton), withText("Create QR Code"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment),
                                        0),
                                2),
                        isDisplayed()))
        materialButton2.perform(click())

        authTypeErrorView.check(matches(withText("")))

        val textInputEditText = onView(
                childAtPosition(
                        childAtPosition(
                                withId(R.id.titleTextinputLayout),
                                0),
                        0))

        onView(withId(R.id.titleTextinputLayout)).check(matches(hasTextInputLayoutHintText(targetContext.resources.getString(R.string.mandatory_info_error))))

        textInputEditText.perform(scrollTo(), replaceText("testwifi"), closeSoftKeyboard())

        val materialButton3 = onView(
                allOf(withId(R.id.createQrCodeButton), withText("Create QR Code"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment),
                                        0),
                                2),
                        isDisplayed()))
        materialButton3.perform(click())

        val textInputEditText2 = onView(
                childAtPosition(
                        childAtPosition(
                                withId(R.id.ssidTextinputLayout),
                                0),
                        0))
        textInputEditText2.perform(scrollTo(), replaceText("testssid"), closeSoftKeyboard())

        onView(withId(R.id.ssidTextinputLayout)).check(matches(hasTextInputLayoutHintText(targetContext.resources.getString(R.string.mandatory_info_error))))

        val materialButton4 = onView(
                allOf(withId(R.id.createQrCodeButton), withText("Create QR Code"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment),
                                        0),
                                2),
                        isDisplayed()))
        materialButton4.perform(click())

        val textInputEditText3 = onView(
                childAtPosition(
                        childAtPosition(
                                withId(R.id.passwordTextinputLayout),
                                0),
                        0))
        onView(withId(R.id.passwordTextinputLayout)).check(matches(hasTextInputLayoutHintText(targetContext.resources.getString(R.string.mandatory_info_error))))
        textInputEditText3.perform(scrollTo(), replaceText("12345678"), closeSoftKeyboard())

        val materialButton5 = onView(
                allOf(withId(R.id.createQrCodeButton), withText("Create QR Code"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment),
                                        0),
                                2),
                        isDisplayed()))
        materialButton5.perform(click())
        onView(allOf(withId(R.id.contentData))).check(matches(withText("WIFI:S:testssid;T:WPA;P:\"12345678\";;")))
    }

    private fun childAtPosition(
            parentMatcher: Matcher<View>, position: Int): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }

    fun hasTextInputLayoutHintText(expectedErrorText: String): Matcher<View> = object : TypeSafeMatcher<View>() {

        override fun describeTo(description: Description?) {}

        override fun matchesSafely(item: View?): Boolean {
            if (item !is TextInputLayout) return false
            val error = item.error ?: return false
            return expectedErrorText == error.toString()
        }
    }

}
