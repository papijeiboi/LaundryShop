package com.jemoje.laundryreservationppm.activities

import android.content.ContextWrapper
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jemoje.laundryreservationppm.R
import com.jemoje.laundryreservationppm.constant.Keys
import com.pixplicity.easyprefs.library.Prefs
import java.util.*
import kotlin.concurrent.timerTask

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Prefs.Builder()
            .setContext(this)
            .setMode(ContextWrapper.MODE_PRIVATE)
            .setPrefsName(packageName)
            .setUseDefaultSharedPreference(true)
            .build()

        runOnUiThread {
            val timer = Timer()
            timer.schedule(timerTask {

                val apiToken = Prefs.getString(Keys.USER_TOKEN, "")

                if (apiToken == "") {
                    runOnUiThread {
                        val intent = Intent(applicationContext, LandingActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        applicationContext.startActivity(intent)
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                    }
                    finish()
                } else {
                    runOnUiThread {
                        val intent = Intent(applicationContext, MainMenuActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        applicationContext.startActivity(intent)
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                    }
                    finish()
                }
            }, 2000)
        }
    }
}
