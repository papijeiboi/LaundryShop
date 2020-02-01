package com.jemoje.laundryreservationppm.webservice

import com.google.gson.GsonBuilder
import com.jemoje.laundryreservationppm.model.UserResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UserService {

    @FormUrlEncoded
    @POST("register-link")
    fun register(
        @Field("email") username: String,
        @Field("password") password: String,
        @Field("password_confirmation") password_confirmation: String
    ): retrofit2.Call<UserResponse>

    @FormUrlEncoded
    @POST("login-link")
    fun login(
        @Field("email") username: String,
        @Field("password") password: String
    ): retrofit2.Call<UserResponse>


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