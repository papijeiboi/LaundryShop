package com.jemoje.laundryreservationppm.model

import com.google.gson.annotations.SerializedName

class TimeBlocksData {

    @SerializedName("start_time")
    var startTime: String? = null

    @SerializedName("end_time")
    var endTime: String? = null

    @SerializedName("allowed")
    var allowed: String? = null
}