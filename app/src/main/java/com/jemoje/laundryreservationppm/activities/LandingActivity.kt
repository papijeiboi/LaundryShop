package com.jemoje.laundryreservationppm.activities

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.jemoje.laundryreservationppm.R
import com.skydoves.powerspinner.IconSpinnerAdapter
import com.skydoves.powerspinner.IconSpinnerItem
import kotlinx.android.synthetic.main.activity_landing.*

class LandingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)

        spn_user.apply {
            setSpinnerAdapter(IconSpinnerAdapter(this))
            setItems(
                arrayListOf(
                    IconSpinnerItem(context.getDrawable(R.drawable.ic_owner), "Owner"),
                    IconSpinnerItem(context.getDrawable(R.drawable.ic_user), "User")

                )
            )
            getSpinnerRecyclerView().layoutManager = GridLayoutManager(context, 1)
            selectItemByIndex(0) // select an item initially.
            lifecycleOwner = this@LandingActivity
        }

        val handleClicks = View.OnClickListener {
            when (it) {
                btn_landing_sign_in -> {
                    runOnUiThread {
                        finish()
                        val intent = Intent(applicationContext, MainMenuActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        applicationContext.startActivity(intent)
                        overridePendingTransition(R.anim.enter_from, R.anim.enter_to)
                    }
                }
                btn_landing_sign_up -> {
                    runOnUiThread {
                        finish()
                        val intent = Intent(applicationContext, SignUpActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        applicationContext.startActivity(intent)
                        overridePendingTransition(R.anim.enter_from, R.anim.enter_to)
                    }

                }
                btn_landing_forgot_password -> {

                }


            }
        }

        btn_landing_sign_in.setOnClickListener(handleClicks)
        btn_landing_sign_up.setOnClickListener(handleClicks)
        btn_landing_forgot_password.setOnClickListener(handleClicks)
    }
}
