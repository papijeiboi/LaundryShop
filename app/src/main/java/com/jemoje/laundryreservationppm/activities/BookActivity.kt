package com.jemoje.laundryreservationppm.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Display
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.aminography.primecalendar.PrimeCalendar
import com.aminography.primecalendar.civil.CivilCalendar
import com.aminography.primecalendar.common.CalendarFactory
import com.aminography.primecalendar.common.CalendarType
import com.aminography.primedatepicker.PickType
import com.aminography.primedatepicker.fragment.PrimeDatePickerBottomSheet
import com.google.gson.Gson
import com.jemoje.laundryreservationppm.R
import com.jemoje.laundryreservationppm.adapters.MachineAdapter
import com.jemoje.laundryreservationppm.adapters.SelectTimeAdapter
import com.jemoje.laundryreservationppm.constant.Keys
import com.jemoje.laundryreservationppm.model.MachineResponseData
import com.jemoje.laundryreservationppm.model.ReservationLaundryResponse
import com.jemoje.laundryreservationppm.model.TimeBlocksData
import com.jemoje.laundryreservationppm.model.TimeBlocksResponse
import com.jemoje.laundryreservationppm.webservice.UserService
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.activity_book.*
import kotlinx.android.synthetic.main.activity_machine.*
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class BookActivity : AppCompatActivity(), PrimeDatePickerBottomSheet.OnDayPickedListener {
    private val TAG = "BookActivity"
    private var timeBlocksData: MutableList<TimeBlocksData> = ArrayList()
    private var datePicker: PrimeDatePickerBottomSheet? = null
    private var selectTimeAdapter: SelectTimeAdapter? = null
    private var date: String? = null
    private var machineId: String? = null
    private var machineShopId: String? = null

    private var apiToken: String? = null
    private var apiService: UserService? = null


    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book)

        apiToken = Prefs.getString(Keys.USER_TOKEN, "")
        apiService = UserService.create(this.getString(R.string.base_url))

        val current = SimpleDateFormat("yyyy-MM-dd")
        val displayDate = SimpleDateFormat("d MMM yyyy")
        date = current.format(Date())
        val displayCurrentDate = displayDate.format(Date())

        tv_book_date.text = displayCurrentDate
        val dataMachine = intent.getStringExtra("machine_data_book")
        val gson = Gson()
        val machineData: MachineResponseData =
            gson.fromJson(dataMachine, MachineResponseData::class.java)
        machineId = machineData.id
        machineShopId = machineData.shopId
        tv_book_machine.text = machineData.machineNumber

        getSelectedTimeBlocksService(date!!, machineId!!, machineShopId!!)

        val today = CalendarFactory.newInstance(CalendarType.CIVIL)
        val handleBookClicks = View.OnClickListener {
            when (it) {
                btn_book_back -> {
                    finish()
                    overridePendingTransition(R.anim.exit_from, R.anim.exit_to)
                }
                btn_book_date -> {
                    datePicker = PrimeDatePickerBottomSheet.newInstance(
                        today,
                        null,
                        null,
                        PickType.SINGLE
                    )
                    datePicker?.setOnDateSetListener(this)
                    datePicker?.show(supportFragmentManager, PICKER_TAG)
                }
                btn_book -> {
                    reserveTimeBlocks.forEach {
                        Log.d(TAG, "Start Time ${it.startTime}")
                        Log.d(TAG, "End Time ${it.endTime}")
                        Log.d(TAG, "Allowed ${it.allowed}")
                        reserveTimeService(
                            machineId!!,
                            date!!,
                            it.startTime!!,
                            it.endTime!!,
                            machineShopId!!
                        )
                    }
                }
            }
        }

        btn_book_back.setOnClickListener(handleBookClicks)
        btn_book_date.setOnClickListener(handleBookClicks)
        btn_book.setOnClickListener(handleBookClicks)

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_book_time.layoutManager = layoutManager
    }

    override fun onMultipleDaysPicked(multipleDays: List<PrimeCalendar>) {
    }

    override fun onRangeDaysPicked(startDay: PrimeCalendar, endDay: PrimeCalendar) {
    }

    @SuppressLint("SimpleDateFormat")
    override fun onSingleDayPicked(singleDay: PrimeCalendar) {
        val fmt = SimpleDateFormat("yyyy/MM/dd")
        val timeDisplay = fmt.parse(singleDay.shortDateString)
        val fmtOut = SimpleDateFormat("d MMM yyyy")
        val timeValue = SimpleDateFormat("yyyy-MM-dd")

        getSelectedTimeBlocksService(timeValue.format(timeDisplay!!), machineId!!, machineShopId!!)
        tv_book_date.text = fmtOut.format(timeDisplay)
        date = timeValue.format(timeDisplay)
    }

    companion object {
        const val PICKER_TAG = "PrimeDatePickerBottomSheet"
    }

    private fun getSelectedTimeBlocksService(date: String, machineId: String, shopId: String) {

        val callService = apiService!!.getTimeBlocks(
            date,
            machineId,
            shopId,
            "application/json",
            "Bearer $apiToken"
        )
        callService.enqueue(object : retrofit2.Callback<TimeBlocksResponse> {
            override fun onFailure(call: Call<TimeBlocksResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }

            override fun onResponse(
                call: Call<TimeBlocksResponse>,
                response: Response<TimeBlocksResponse>
            ) {
                if (response.isSuccessful) {
                    when (response.code()) {
                        200 -> {
                            timeBlocksData = response.body()!!.timeBlocks!!
                            selectTimeAdapter = SelectTimeAdapter(this@BookActivity, timeBlocksData)
                            rv_book_time.adapter = selectTimeAdapter

                        }
                        else -> {
                            Log.e(TAG, "callWebService responseCode: ${response.code()}")
                        }
                    }

                } else {
                    when (response.code()) {
                        400 -> {
                            Log.e(TAG, "callWebService onResponse 400: Something went wrong!")
                        }
                        401 -> {
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

    private var reserveTimeBlocks: MutableList<TimeBlocksData> = ArrayList()
    fun selectedMakeFalse(position: Int) {
        timeBlocksData[position].allowed = false
        reserveTimeBlocks.add(timeBlocksData[position])
        rv_book_time.adapter!!.notifyDataSetChanged()
    }

    fun selectedMakeTrue(position: Int) {
        timeBlocksData[position].allowed = true
        reserveTimeBlocks.remove(timeBlocksData[position])
        rv_book_time.adapter!!.notifyDataSetChanged()
    }

    private fun reserveTimeService(
        machineId: String,
        date: String,
        timeIn: String,
        timeOut: String,
        shopId: String
    ) {
        val callService = apiService!!.reserveTime(
            machineId, date, timeIn, timeOut, shopId, "application/json",
            "Bearer $apiToken"
        )

        callService.enqueue(object : retrofit2.Callback<ReservationLaundryResponse> {
            override fun onFailure(call: Call<ReservationLaundryResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }

            override fun onResponse(
                call: Call<ReservationLaundryResponse>,
                response: Response<ReservationLaundryResponse>
            ) {
                if (response.isSuccessful) {
                    when (response.code()) {
                        200 -> {
                            val fmt = SimpleDateFormat("yyyy-MM-dd")
                            val timeDisplay = fmt.parse(response.body()!!.reservedDate)
                            val fmtOut = SimpleDateFormat("d MMM yyyy")
                            val displayDate = fmtOut.format(timeDisplay)
                            displayDialog("Successfully Reserved! @Machine # ${response.body()!!.machine!!.machineNumber} Date: $displayDate")
                        }
                        else -> {
                            Log.e(TAG, "callWebService responseCode: ${response.code()}")
                        }
                    }

                } else {
                    when (response.code()) {
                        400 -> {
                            Log.e(TAG, "callWebService onResponse 400: Something went wrong!")
                        }
                        422 -> {
                            displayDialog("Conflict Time")
                        }
                        else -> {
                            Log.e(
                                TAG,
                                "callWebService onResponse else: Something went wrong! ${response.errorBody()}"
                            )
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
}
