package com.jemoje.laundryreservationppm.webservice

import com.google.gson.GsonBuilder
import com.jemoje.laundryreservationppm.model.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*

interface UserService {

    @FormUrlEncoded
    @POST("/api/register")
    fun register(
        @Field("name") name: String,
        @Field("email") username: String,
        @Field("password") password: String,
        @Field("password_confirmation") password_confirmation: String,
        @Header("Accept") accept: String
    ): retrofit2.Call<UserResponse>

    @FormUrlEncoded
    @POST("/api/login")
    fun login(
        @Field("email") username: String,
        @Field("password") password: String
    ): retrofit2.Call<UserResponse>

    @GET("/api/shop")
    fun getReservation(
        @Query("sort_order") sortOrder: String,
        @Header("Accept") accept: String,
        @Header("Authorization") authHeader: String
    ): retrofit2.Call<ReservationResponse>

    @GET("/api/shop")
    fun getSearchReservation(
        @Query("search") search: String,
        @Query("sort_order") sortOrder: String,
        @Header("Accept") accept: String,
        @Header("Authorization") authHeader: String
    ): retrofit2.Call<ReservationResponse>

    @GET("/api/machine")
    fun getMachines(
        @Query("shop_id") shopId: String,
        @Query("sort_order") sortOrder: String,
        @Header("Accept") accept: String,
        @Header("Authorization") authHeader: String
    ):retrofit2.Call<MachineResponse>

    @GET("/api/getAllowedTimeBlock")
    fun getTimeBlocks(
        @Query("date") date:String,
        @Query("machine_id") machineId:String,
        @Query("shop_id") shopId:String,
        @Header("Accept") accept: String,
        @Header("Authorization") authHeader: String
    ):retrofit2.Call<TimeBlocksResponse>

    @FormUrlEncoded
    @POST("/api/reservation")
    fun reserveTime(
        @Field("machine_id") machineId: String,
        @Field("reserved_date") reservedDate: String,
        @Field("reserved_time_in") reservedTimeIn: String,
        @Field("reserved_time_out") reservedTimeOut: String,
        @Field("shop_id") shopId: String,
        @Header("Accept") accept: String,
        @Header("Authorization") authHeader: String
    ):retrofit2.Call<ReservationLaundryResponse>

    @GET("/api/my-reservation")
    fun getMyReservation(
        @Header("Accept") accept: String,
        @Header("Authorization") authHeader: String
    ):retrofit2.Call<MutableList<MyReservationResponse>>


    companion object Factory {
        var gson = GsonBuilder()
            .setLenient()
            .create()

        fun create(baseUrl: String): UserService {
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
            return retrofit.create(UserService::class.java)
        }
    }
}