package com.jemoje.laundryreservationppm.fragment


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.jemoje.laundryreservationppm.R
import com.jemoje.laundryreservationppm.adapters.MyReservationAdapter
import com.jemoje.laundryreservationppm.constant.Keys
import com.jemoje.laundryreservationppm.model.MyReservationResponse
import com.jemoje.laundryreservationppm.webservice.UserService
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.fragment_my_reservation.view.*
import retrofit2.Call
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class MyReservationFragment : Fragment() {
    private val TAG = "MyReservationFragment"
    private var myReservationAdapter: MyReservationAdapter? = null
    private var myReservationResponseData: MutableList<MyReservationResponse> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val realView = inflater.inflate(R.layout.fragment_my_reservation, container, false)

        initRecyclerView(realView)
        getMyReservationService(realView)
        return realView
    }

    private fun initRecyclerView(realView: View) {
        val mLayoutManagerHome = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        realView.rv_my_reservation.layoutManager = mLayoutManagerHome
//        realView.rv_shops.adapter = reservationAdapter
    }

    private fun getMyReservationService(realView: View) {
        val apiToken = Prefs.getString(Keys.USER_TOKEN, "")
        val apiService = UserService.create(this.getString(R.string.base_url))

        val callService = apiService.getMyReservation("application/json", "Bearer $apiToken")

        callService.enqueue(object : retrofit2.Callback<MutableList<MyReservationResponse>> {
            override fun onFailure(call: Call<MutableList<MyReservationResponse>>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }

            override fun onResponse(
                call: Call<MutableList<MyReservationResponse>>,
                response: Response<MutableList<MyReservationResponse>>
            ) {
                when {
                    response.code() == 200 -> {

                        myReservationResponseData = response.body()!!
                        myReservationAdapter =
                            MyReservationAdapter(
                                this@MyReservationFragment,
                                myReservationResponseData
                            )
                        realView.rv_my_reservation.adapter = myReservationAdapter
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

}