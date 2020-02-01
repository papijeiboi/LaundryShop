package com.jemoje.laundryreservationppm.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jemoje.laundryreservationppm.R
import kotlinx.android.synthetic.main.activity_about.*

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        btn_about_back.setOnClickListener {
            finish()
            val intent = Intent(applicationContext, MainMenuActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            applicationContext.startActivity(intent)
            overridePendingTransition(R.anim.exit_from, R.anim.exit_to)
        }
    }
}
