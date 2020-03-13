package com.jemoje.laundryreservationppm.model

import com.google.gson.annotations.SerializedName

class MyReservationResponse {

    @SerializedName("id")
    var id: String? = null

    @SerializedName("shop_id")
    var shopId: String? = null

    @SerializedName("machine_id")
    var machineId: String? = null

    @SerializedName("reserved_date")
    var reservedDate: String? = null

    @SerializedName("reserved_time_in")
    var reservedTimeIn: String? = null

    @SerializedName("reserved_time_out")
    var reservedTimeOut: String? = null

    @SerializedName("deleted_at")
    var deleted_at: String? = null

    @SerializedName("created_at")
    var created_at: String? = null

    @SerializedName("updated_at")
    var updated_at: String? = null

    @SerializedName("user_id")
    var user_id: String? = null

    @SerializedName("status")
    var status: String? = null

    @SerializedName("shop")
    var shop: ShopData? = null

    @SerializedName("machine")
    var machine: MachineData? = null
}