package com.jemoje.laundryreservationppm.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.GsonBuilder
import com.jemoje.laundryreservationppm.R
import com.jemoje.laundryreservationppm.constant.Keys
import com.jemoje.laundryreservationppm.model.UserResponse
import com.jemoje.laundryreservationppm.webservice.UserService
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response


class SignUpActivity : AppCompatActivity() {
    private val TAG = "SignUpActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val handleClicks = View.OnClickListener {
            when (it) {
                btn_sign_up_sign_up -> {
                    val name = edt_sign_name.text.toString().trim()
                    val email = edt_sign_email.text.toString().trim()
                    val password = edt_sign_password.text.toString().trim()
                    val confirmPassword = edt_sign_confirm_password.text.toString().trim()

                    if (name.isEmpty() && email.isEmpty() && password.isEmpty()) {
                        displayDialog("Fields must not be empty!")
                    } else if (name.isEmpty()) {
                        displayDialog("Name must not be empty!")
                    } else if (email.isEmpty()) {
                        displayDialog("Email must not be empty!")
                    } else if (password.isEmpty()) {
                        displayDialog("Password must not be empty!")
                    } else if (confirmPassword.isEmpty()) {
                        displayDialog("Confirmation Password must not be empty!")
                    } else if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {


                        if (isEmailValid(email)) {
                            if (password == confirmPassword) {
                                if(password.length >= 8 && confirmPassword.length >= 8){
                                    progress_layout_sign_up.visibility = View.VISIBLE
                                    callWebService(name, email, password, confirmPassword)
                                }else{
                                    displayDialog("The password must be at least 8 characters.")
                                }
                            } else {
                                displayDialog("Password does not match!")
                            }
                        } else {
                            displayDialog("Please enter valid email!")
                        }
                    }
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

    private fun callWebService(
        name: String,
        email: String,
        password: String,
        confirmPassword: String
    ) {
        val apiService = UserService.create(this.getString(R.string.base_url))

        val callService =
            apiService.register(name, email, password, confirmPassword, "application/json")

        callService.enqueue(object : retrofit2.Callback<UserResponse> {
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e(TAG, "CallWebService: ${t.message}")
                progress_layout_sign_up.visibility = View.GONE
            }

            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                progress_layout_sign_up.visibility = View.GONE
                if (response.isSuccessful) {
                    when {
                        response.code() == 200 -> {
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
                                mGson.toJson(response.body())
                            )
                            runOnUiThread {
                                val intent =
                                    Intent(applicationContext, MainMenuActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                applicationContext.startActivity(intent)
                                overridePendingTransition(R.anim.enter_from, R.anim.enter_to)
                                finish()
                            }

                        }
                        else -> {
                            try {
                                val jObjError = JSONObject(response.errorBody()!!.string())

                                Log.e(
                                    TAG,
                                    "${jObjError.getJSONObject("error").getString("message")}"
                                )
                            } catch (e: Exception) {
                                Log.e(TAG, e.message!!)
                            }
                            Log.e(TAG, "${response.errorBody()} ${response.code()}")
                            displayDialog("The given data was invalid.")
                        }
                    }
                } else {
                    when {
                        response.code() == 422 -> {
                            try {
                                val jObjError = JSONObject(response.errorBody()!!.string())

                                Log.e(
                                    TAG, "not"+
                                            "${jObjError.getJSONObject("errors")}"
                                )
                            } catch (e: Exception) {
                                Log.e(TAG, "e"+e.message!!)
                            }
                            Log.e(TAG, "${response.errorBody()} ${response.code()}")
                            displayDialog("The given data was invalid.")
                        }
                        else -> {
                            try {
                                val jObjError = JSONObject(response.errorBody()!!.string())

                                Log.e(
                                    TAG, "not"+
                                    "${jObjError.getJSONObject("errors")}"
                                )
                            } catch (e: Exception) {
                                Log.e(TAG, "e"+e.message!!)
                            }
                            Log.e(TAG, "${response.errorBody()} ${response.code()}")
                            displayDialog("The given data was invalid.")
                        }
                    }
                }
            }
    })
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

private fun isEmailValid(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

}
