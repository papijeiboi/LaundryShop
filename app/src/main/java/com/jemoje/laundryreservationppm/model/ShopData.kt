package com.jemoje.laundryreservationppm.model

import com.google.gson.annotations.SerializedName

class ShopData {


    @SerializedName("id")
    var id: String? = null

    @SerializedName("user_id")
    var userId: String? = null

    @SerializedName("shop_name")
    var email: String? = null

    @SerializedName("price_type")
    var priceType: String? = null

    @SerializedName("price")
    var price: String? = null

    @SerializedName("address")
    var address: String? = null

    @SerializedName("lat")
    var lat: String? = null

    @SerializedName("long")
    var long: String? = null

    @SerializedName("deleted_at")
    var deletedAt: String? = null

    @SerializedName("created_at")
    var createdAt: String? = null

    @SerializedName("updated_at")
    var updatedAt: String? = null

    @SerializedName("start_day")
    var startDay: String? = null

    @SerializedName("end_day")
    var endDay: String? = null

    @SerializedName("opening_time")
    var openingTime: String? = null

    @SerializedName("closing_time")
    var closingTime: String? = null
}