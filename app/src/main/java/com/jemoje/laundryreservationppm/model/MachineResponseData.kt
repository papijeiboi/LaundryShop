package com.jemoje.laundryreservationppm.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MachineResponseData {

    @SerializedName("id")
    var id: String? = null

    @SerializedName("user_id")
    var userId: String? = null

    @SerializedName("shop_id")
    var shopId: String? = null

    @SerializedName("machine_number")
    var machineNumber: String? = null

    @SerializedName("specification")
    var specification: String? = null

    @SerializedName("deleted_at")
    var deletedAt: String? = null

    @SerializedName("created_at")
    var createdAt: String? = null

    @SerializedName("updated_at")
    var updatedAt: String? = null

    @SerializedName("reservations")
    @Expose
    var reservations: MutableList<ReservationData>? = null
}