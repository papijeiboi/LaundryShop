package com.jemoje.laundryreservationppm.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TimeBlocksResponse {

    @SerializedName("time_blocks")
    @Expose
    var timeBlocks: MutableList<TimeBlocksData>? = null
}