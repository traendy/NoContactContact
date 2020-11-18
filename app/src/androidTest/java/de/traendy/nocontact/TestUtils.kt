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


class IDTScreenCaptureProcessor(parentFolderPath: String) : BasicScreenCaptureProcessor(
) {
    init {
        mTag = "IDTScreenCaptureProcessor"
        mFileNameDelimiter = "-"
        mDefaultFilenamePrefix = "NoContactScreenshots"
        this.mDefaultScreenshotPath = File(
            File(
                getInstrumentation().targetContext.getExternalFilesDir(DIRECTORY_PICTURES),
                "espresso_screenshots"
            ).absolutePath,
            "screenshots/$parentFolderPath"
        )
    }

    override fun getFilename(prefix: String): String = prefix
}

fun takeScreenshot(parentFolderPath: String = "", screenShotName: String) {
    Log.d("Screenshots", "Taking screenshot of '$screenShotName'")
    val screenCapture = Screenshot.capture()
    val processors = setOf(IDTScreenCaptureProcessor(parentFolderPath))
    try {
        screenCapture.apply {
            name = screenShotName
            process(processors)
        }
        Log.d("Screenshots", "Screenshot taken")
    } catch (ex: IOException) {
        Log.e("Screenshots", "Could not take the screenshot", ex)
    }
}

class ScreenshotTestWatcher : TestWatcher() {
    override fun failed(e: Throwable?, description: Description?) {
        super.failed(e, description)
        val parentFolderPath = "failures/${description?.className ?: "missingDescription"}"
        takeScreenshot(
            parentFolderPath = parentFolderPath,
            screenShotName = description?.methodName ?: "missingMethodName"
        )
    }
}