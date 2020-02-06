package com.jemoje.laundryreservationppm.activities

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.GsonBuilder
import com.jemoje.laundryreservationppm.R
import com.jemoje.laundryreservationppm.constant.Keys
import com.jemoje.laundryreservationppm.model.UserResponse
import com.jemoje.laundryreservationppm.webservice.UserService
import com.pixplicity.easyprefs.library.Prefs
import com.skydoves.powerspinner.IconSpinnerAdapter
import com.skydoves.powerspinner.IconSpinnerItem
import kotlinx.android.synthetic.main.activity_landing.*
import retrofit2.Call
import retrofit2.Response

class LandingActivity : AppCompatActivity() {
    private val TAG = "LandingActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)


        val handleClicks = View.OnClickListener {
            when (it) {
                btn_landing_sign_in -> {
                    runOnUiThread {
                        val email = edt_landing_email.text.toString().trim()
                        val password = edt_landing_password.text.toString().trim()
                        validateEmailPassword(email, password)
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

    private fun validateEmailPassword(email: String, password:String){
        if (email.isEmpty() && password.isEmpty()) {
            displayDialog("Email and Password must not be empty!")
        } else if (email.isEmpty() && password.isNotEmpty()) {
            displayDialog("Email must not be empty!")
        } else if (email.isNotEmpty() && password.isEmpty()) {
            displayDialog("Password must not be empty!")
        } else if (email.isNotEmpty() && password.isNotEmpty()) {

            if (isEmailValid(email)) {
                progress_layout_landing.visibility = View.VISIBLE
                callWebService(email, password)
            } else {
                displayDialog("Please enter valid email.")
            }

        }
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun displayDialog(message: String) {
        val builder = AlertDialog.Builder(this)

        builder.setMessage(message)


        builder.setPositiveButton("Ok") { dialog, which ->
            dialog.dismiss()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun callWebService(email: String, password: String){

        val apiService = UserService.create(this.getString(R.string.base_url))
        val callService = apiService.login(email, password)

        callService.enqueue(object : retrofit2.Callback<UserResponse>{
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e(TAG, " onFailure: ${t.message}")
            }

            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful){
                    when(response.code()){
                        200 -> {
                            val userResponse: UserResponse? = response.body()

                            Prefs.putString(
                                Keys.USER_TOKEN,
                                userResponse!!.token!!.accessToken
                            )

                            val mGson = GsonBuilder()
                                .setLenient()
                                .create()
                            Prefs.putString(
                                Keys.USER_FULL_DATA,
                                mGson.toJson(response.body()!!.user)
                            )

                            runOnUiThread {
                                finish()
                                val intent =
                                    Intent(applicationContext, MainMenuActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                applicationContext.startActivity(intent)
                                overridePendingTransition(R.anim.enter_from, R.anim.enter_to)
                            }
                        }
                        401 -> {
                            Log.e(TAG, " 401: ${response.errorBody()}")
                            displayDialog("Invalid Email or Password")
                        }
                        else -> {
                            Log.e(TAG, " else: ${response.errorBody()}")
                            displayDialog("Invalid Email or Password")
                        }
                    }
                }else{
                    Log.e(TAG, " isNotSuccessful: ${response.errorBody()}")
                    displayDialog("Something went wrong!")
                }
                progress_layout_landing.visibility = View.GONE
            }
        })

    }
}
