package de.traendy.nocontact

import android.util.Log
import androidx.test.runner.screenshot.BasicScreenCaptureProcessor
import androidx.test.runner.screenshot.Screenshot
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import java.io.IOException

const val TAG = "TestUtils"

fun takeScreenshot(screenShotName: String) {
    Log.d(TAG, "Taking screenshot of '$screenShotName'")
    val screenCapture = Screenshot.capture()
    val processors = setOf(BasicScreenCaptureProcessor())
    try {
        screenCapture.apply {
            name = screenShotName
            process(processors)
        }
        Log.d(TAG, "Screenshot taken")
    } catch (ex: IOException) {
        Log.e(TAG, "Could not take a screenshot", ex)
    }
}

class ScreenshotTestWatcher : TestWatcher() {

    private var failed = false

    override fun failed(e: Throwable?, description: Description) {
        takeScreenshot(screenShotName = description.methodName)
        failed = true
        super.failed(e, description)
    }

    override fun finished(description: Description) {
        if (!failed) {
            takeScreenshot(screenShotName = description.methodName)
        }
        failed = false
        super.finished(description)
    }
}
