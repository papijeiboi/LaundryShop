package com.jemoje.laundryreservationppm.model

import com.google.gson.annotations.SerializedName

class ReservationData {

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
    var deletedAt: String? = null

    @SerializedName("created_at")
    var createdAt: String? = null

    @SerializedName("updated_at")
    var updatedAt: String? = null

    @SerializedName("user_id")
    var userId: String? = null

    @SerializedName("status")
    var status: String? = null
}