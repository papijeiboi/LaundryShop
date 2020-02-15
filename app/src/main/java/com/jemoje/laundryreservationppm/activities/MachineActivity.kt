package com.jemoje.laundryreservationppm.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowId
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jemoje.laundryreservationppm.R
import com.jemoje.laundryreservationppm.adapters.MachineAdapter
import com.jemoje.laundryreservationppm.constant.Keys
import com.jemoje.laundryreservationppm.helper.GridSpacingItemDecoration
import com.jemoje.laundryreservationppm.model.MachineResponse
import com.jemoje.laundryreservationppm.model.MachineResponseData
import com.jemoje.laundryreservationppm.webservice.UserService
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.activity_machine.*
import retrofit2.Call
import retrofit2.Response

class MachineActivity : AppCompatActivity() {
    private val TAG = "MachineActivity"
    private var machineAdapter: MachineAdapter? = null
    private var machineResponseData: MutableList<MachineResponseData> = ArrayList()
    private var machineId:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_machine)

        machineId = intent.getStringExtra("machine_id")!!
        machineService(machineId!!)

        btn_machine_back.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.exit_from, R.anim.exit_to)
        }



        initRecyclerView()
    }

    private fun initRecyclerView(){
        var spanSpace = 16
        var spanCount = 145f
        val numCol = calculateNoOfColumns(this,spanCount)
        rv_machines.layoutManager = GridLayoutManager(this, numCol)
        rv_machines.addItemDecoration(GridSpacingItemDecoration(numCol,spanSpace, true))

    }

    private fun calculateNoOfColumns(context: Context, columnWidthDp: Float): Int { // For example columnWidthdp=180
        val displayMetrics = context.getResources().getDisplayMetrics()
        val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
        return (screenWidthDp / columnWidthDp + 0.5).toInt()
    }

    private fun machineService(shopId: String){
        val apiToken = Prefs.getString(Keys.USER_TOKEN, "")
        val apiService = UserService.create(this.getString(R.string.base_url))

        val callService = apiService.getMachines(shopId,"asc", "application/json", "Bearer $apiToken")
        callService.enqueue(object: retrofit2.Callback<MachineResponse>{
            override fun onFailure(call: Call<MachineResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }

            override fun onResponse(
                call: Call<MachineResponse>,
                response: Response<MachineResponse>
            ) {
                if (response.isSuccessful){
                    when(response.code()){
                        200 -> {
                            machineResponseData = response.body()!!.data!!
                            machineAdapter = MachineAdapter(this@MachineActivity, machineResponseData)
                            rv_machines.adapter = machineAdapter

                        }
                        else ->{
                            Log.e(TAG, "callWebService responseCode: ${response.code()}")
                        }
                    }

                }else{
                    when(response.code()){
                        400 ->{
                            Log.e(TAG, "callWebService onResponse 400: Something went wrong!")
                        }
                        401 ->{
                            Log.e(TAG, "callWebService onResponse 401: Something went wrong!")
                        }
                        else -> {
                            Log.e(TAG, "callWebService onResponse else: Something went wrong!")
                        }
                    }
                }
            }
        })
    }
}
