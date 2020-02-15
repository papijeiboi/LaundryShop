package com.jemoje.laundryreservationppm.fragment


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder

import com.jemoje.laundryreservationppm.R
import com.jemoje.laundryreservationppm.adapters.ReservationAdapter
import com.jemoje.laundryreservationppm.constant.Keys
import com.jemoje.laundryreservationppm.model.ReservationResponse
import com.jemoje.laundryreservationppm.model.ReservationResponseData
import com.jemoje.laundryreservationppm.webservice.UserService
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.fragment_reservation.*
import kotlinx.android.synthetic.main.fragment_reservation.view.*
import retrofit2.Call
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class ReservationFragment : Fragment() {
    private val TAG = "ReservationFragment"
    private var reservationAdapter: ReservationAdapter? = null
    private var reservationResponseData: MutableList<ReservationResponseData> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val realView = inflater.inflate(R.layout.fragment_reservation, container, false)

        realView.edt_reservation_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                searchReservationService(p0.toString(),this@ReservationFragment,realView)
            }
        })

        initRecyclerView(realView)
        reservationService(this, realView)

        return realView
    }

    private fun reservationService(reservationFragment: ReservationFragment, realView: View) {

        val apiToken = Prefs.getString(Keys.USER_TOKEN, "")
        val apiService = UserService.create(this.getString(R.string.base_url))

        val callService = apiService.getReservation("asc", "application/json", "Bearer $apiToken")
        callService.enqueue(object : retrofit2.Callback<ReservationResponse> {
            override fun onFailure(call: Call<ReservationResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }

            override fun onResponse(
                call: Call<ReservationResponse>,
                response: Response<ReservationResponse>
            ) {
                    when {
                        response.code() == 200 -> {

                            reservationResponseData = response.body()!!.data!!
                            reservationAdapter =
                                ReservationAdapter(reservationFragment, reservationResponseData)
                            realView.rv_shops.adapter = reservationAdapter
//                            filmSceneResponseData = response.body()!!.responseData!!.data!!
//                            filmAdapter = FilmAdapter(filmMenuFragment, filmSceneResponseData)
//                            realView.rv_film.adapter = filmAdapter


//                            val mGson = GsonBuilder()
//                                .setLenient()
//                                .create()
//                            val filmSaved = response.body()!!.responseData!!.data!!
//                            Prefs.putString(keys.FILM_DATA, mGson.toJson(filmSaved))
                        }
                        else -> {
                            Log.e(TAG, "callWebService responseCode: ${response.errorBody()}")
                        }
                    }
            }
        })


    }

    private fun searchReservationService(search: String,reservationFragment: ReservationFragment, realView: View){
        val apiToken = Prefs.getString(Keys.USER_TOKEN, "")
        val apiService = UserService.create(this.getString(R.string.base_url))

        val callService = apiService.getSearchReservation(search,"asc", "application/json", "Bearer $apiToken")
        callService.enqueue(object : retrofit2.Callback<ReservationResponse> {
            override fun onFailure(call: Call<ReservationResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }

            override fun onResponse(
                call: Call<ReservationResponse>,
                response: Response<ReservationResponse>
            ) {
                if (response.isSuccessful) {
                    when {
                        response.code() == 200 -> {

                            reservationResponseData = response.body()!!.data!!
                            reservationAdapter =
                                ReservationAdapter(reservationFragment, reservationResponseData)
                            realView.rv_shops.adapter = reservationAdapter
                            realView.rv_shops.adapter!!.notifyDataSetChanged()
//                            filmSceneResponseData = response.body()!!.responseData!!.data!!
//                            filmAdapter = FilmAdapter(filmMenuFragment, filmSceneResponseData)
//                            realView.rv_film.adapter = filmAdapter


//                            val mGson = GsonBuilder()
//                                .setLenient()
//                                .create()
//                            val filmSaved = response.body()!!.responseData!!.data!!
//                            Prefs.putString(keys.FILM_DATA, mGson.toJson(filmSaved))
                        }
                        else -> {
                            Log.e(TAG, "callWebService responseCode: ${response.errorBody()}")
                        }
                    }
                } else {
                    Log.e(TAG, "callWebService onResponse: Something went wrong!")
                }
            }
        })
    }

    private fun initRecyclerView(realView: View) {

        val mLayoutManagerHome = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        realView.rv_shops.layoutManager = mLayoutManagerHome
//        realView.rv_shops.adapter = reservationAdapter
    }


}
