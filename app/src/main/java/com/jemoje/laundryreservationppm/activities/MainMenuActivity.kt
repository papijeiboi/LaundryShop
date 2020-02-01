package com.jemoje.laundryreservationppm.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import com.jemoje.laundryreservationppm.R
import com.jemoje.laundryreservationppm.fragment.MyReservationFragment
import com.jemoje.laundryreservationppm.fragment.ReservationFragment
import kotlinx.android.synthetic.main.activity_main_menu.*
import kotlinx.android.synthetic.main.layout_drawer_custom.*


class MainMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        initBottomNav()

        val drawerClicks = View.OnClickListener {
            when(it){
                drawer_profile ->{
                    toggle()
                    val intent = Intent(applicationContext, ProfileActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    applicationContext.startActivity(intent)
                    overridePendingTransition(R.anim.enter_from, R.anim.enter_to)
                }

                drawer_about -> {
                    toggle()
                    val intent = Intent(applicationContext, AboutActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    applicationContext.startActivity(intent)
                    overridePendingTransition(R.anim.enter_from, R.anim.enter_to)
                }

                drawer_logout -> {

                }

                btn_menu -> {
                    toggle()
                }
            }
        }

        drawer_profile.setOnClickListener(drawerClicks)
        drawer_about.setOnClickListener(drawerClicks)
        drawer_logout.setOnClickListener(drawerClicks)
        btn_menu.setOnClickListener(drawerClicks)




    }

    private fun toggle() {
        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    private fun initBottomNav(){

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, ReservationFragment(), "reservation").commit()

        bottom_nav.setOnNavigationItemSelectedListener{ item ->
            when(item.itemId){
                R.id.bot_reservation -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, ReservationFragment(), "reservation").commit()
                }
                R.id.bot_my_reservation -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, MyReservationFragment(), "my_reservation").commit()
                }
            }
            true
        }
    }
}
