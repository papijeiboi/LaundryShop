package com.jemoje.laundryreservationppm.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jemoje.laundryreservationppm.R
import kotlinx.android.synthetic.main.activity_success.*

class SuccessActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success)

        val displayDate=intent.getStringExtra("displayDate")
        val machineNumber=intent.getStringExtra("machineNumber")

        tv_success_machine_number.text = machineNumber
        tv_success_date.text = displayDate

        btn_success_done.setOnClickListener{
            val intent = Intent(this, MainMenuActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.enter_from, R.anim.enter_to)
            finish()
        }

    }
}
