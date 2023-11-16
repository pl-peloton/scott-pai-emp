package com.example.android.testing.uiautomator.BasicSample.tests

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.test.InstrumentationRegistry
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SdkSuppress
import androidx.test.runner.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.BySelector
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiScrollable
import androidx.test.uiautomator.UiSelector
import androidx.test.uiautomator.Until
import com.google.common.reflect.Reflection.getPackageName
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 18)

class UIAutomatorDemo {

//    companion object {
//        const val GOOGLE_SETTING = "Google"
//        const val ADS_SETTING = "Ads"
//        const val RESET_ADVERTISING_ID = "Reset advertising ID"
//    }

    private val BASIC_SAMPLE_PACKAGE = "com.example.android.testing.uiautomator.BasicSample"
    private val LAUNCH_TIMEOUT = 5000
    private val STRING_TO_BE_TYPED = "UiAutomator"
    private val mDevice: UiDevice? = null

    private fun getLauncherPackageName(): String? {
        // Create launcher Intent
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)

        // Use PackageManager to get the launcher package name
        val pm = ApplicationProvider.getApplicationContext<Context>().packageManager
        val resolveInfo = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY)
        return resolveInfo!!.activityInfo.packageName
    }

    @Before
    open fun startMainActivityFromHomeScreen() {
        // Initialize UiDevice instance
        val device = UiDevice.getInstance(androidx.test.platform.app.InstrumentationRegistry.getInstrumentation())
        // Start from the home screen
        device.pressHome()
        // Wait for launcher
        val launcherPackage: String? = getLauncherPackageName()
        Assert.assertThat(launcherPackage, CoreMatchers.notNullValue())
        device.wait<Boolean>(
            Until.hasObject(By.pkg(launcherPackage).depth(0)),
            this.LAUNCH_TIMEOUT.toLong()
        )
        // Launch the blueprint app
        val context = ApplicationProvider.getApplicationContext<Context>()
        val intent = context.packageManager
            .getLaunchIntentForPackage(this.BASIC_SAMPLE_PACKAGE)
        intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK) // Clear out any previous instances
        context.startActivity(intent)
        // Wait for the app to appear
        device.wait<Boolean>(
            Until.hasObject(
                By.pkg(this.BASIC_SAMPLE_PACKAGE).depth(0)
            ), this.LAUNCH_TIMEOUT.toLong()
        )
    }

    @Test
    fun changeTextBehavior() {
//        get the currently running device
        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        val textToBeChanged = device.findObject(By.res(this.BASIC_SAMPLE_PACKAGE, "textToBeChanged"))
        val userInput = device.findObject(By.res(this.BASIC_SAMPLE_PACKAGE, "editTextUserInput"))
        val changeTextButton = device.findObject(By.res(this.BASIC_SAMPLE_PACKAGE, "changeTextBt"))
        userInput.setText("Scott wuz here")
        changeTextButton.click()
        textToBeChanged.hasObject(By.text("Scott wuz here"))
    }
}