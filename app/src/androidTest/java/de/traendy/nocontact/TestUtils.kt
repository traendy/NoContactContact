package de.traendy.nocontact

import android.os.Environment.DIRECTORY_PICTURES
import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.runner.screenshot.BasicScreenCaptureProcessor
import androidx.test.runner.screenshot.Screenshot
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import java.io.File
import java.io.IOException

const val TAG = "TestUtils"

class IDTScreenCaptureProcessor(parentFolderPath: String) : BasicScreenCaptureProcessor() {

    init {
        this.mDefaultScreenshotPath = File(
                File(
                        getInstrumentation().targetContext.getExternalFilesDir(DIRECTORY_PICTURES),
                        "noCoNoCoTests"
                ).absolutePath,
                "screenshots/$parentFolderPath"
        )
    }

    override fun getFilename(prefix: String): String = prefix
}

fun takeScreenshot(parentFolderPath: String = "", screenShotName: String) {
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

    override fun failed(e: Throwable?, description: Description) {
        val parentFolderPath = "failures2/${description.className}"
        takeScreenshot(parentFolderPath = parentFolderPath, screenShotName = description.methodName)
        super.failed(e, description)
    }

    override fun finished(description: Description) {
        val parentFolderPath = "success/${description.className}"
        takeScreenshot(parentFolderPath = parentFolderPath, screenShotName = description.methodName)
        super.finished(description)
    }
}
