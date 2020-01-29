package com.jemoje.laundryreservationppm.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.jemoje.laundryreservationppm.R
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val handleClicks = View.OnClickListener {
            when(it){
                btn_sign_up_sign_up -> {
                    finish()
                    val intent = Intent(applicationContext, MainMenuActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    applicationContext.startActivity(intent)
                    overridePendingTransition(R.anim.enter_from, R.anim.enter_to)
                }
                btn_sign_up_back -> {
                    finish()
                    val intent = Intent(applicationContext, LandingActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    applicationContext.startActivity(intent)
                    overridePendingTransition(R.anim.exit_from, R.anim.exit_to)
                }
            }
        }

        btn_sign_up_sign_up.setOnClickListener(handleClicks)
        btn_sign_up_back.setOnClickListener(handleClicks)
    }
}
