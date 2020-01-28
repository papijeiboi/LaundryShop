package com.jemoje.laundryreservationppm.activities

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.jemoje.laundryreservationppm.R
import com.skydoves.powerspinner.IconSpinnerAdapter
import com.skydoves.powerspinner.IconSpinnerItem
import kotlinx.android.synthetic.main.activity_landing.*

class LandingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)

        val drawOne = getDrawable(R.drawable.ic_owner)

        spn_user.apply {
            setSpinnerAdapter(IconSpinnerAdapter(this))
            setItems(
                arrayListOf(
                    IconSpinnerItem(context.getDrawable(R.drawable.ic_owner), "Owner"),
                    IconSpinnerItem(context.getDrawable(R.drawable.ic_user), "User")

                ))
            getSpinnerRecyclerView().layoutManager = GridLayoutManager(context, 1)
            selectItemByIndex(0) // select an item initially.
            lifecycleOwner = this@LandingActivity
        }
    }
}
