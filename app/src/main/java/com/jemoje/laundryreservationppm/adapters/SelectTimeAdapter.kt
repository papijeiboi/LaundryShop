package com.jemoje.laundryreservationppm.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jemoje.laundryreservationppm.R
import com.jemoje.laundryreservationppm.activities.BookActivity
import com.jemoje.laundryreservationppm.model.MachineResponseData
import com.jemoje.laundryreservationppm.model.TimeBlocksData
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter

class SelectTimeAdapter constructor(
    bookAct: BookActivity,
    timeData: MutableList<TimeBlocksData>
) : RecyclerView.Adapter<SelectTimeAdapter.ItemViewHolder>() {

    private val timeDataList: MutableList<TimeBlocksData> = timeData
    private val bookActivity = bookAct

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SelectTimeAdapter.ItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item_time, parent, false)


        return ItemViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return timeDataList.size
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        val time = timeDataList[position].startTime
        val fmt = SimpleDateFormat("HH:mm:ss")
        val timeDisplay = fmt.parse(time)
        val fmtOut = SimpleDateFormat("h:mm a")
        holder.timeTitle.text = fmtOut.format(timeDisplay)

        if (timeDataList[position].allowed == true){
            holder.timeQuarter.setBackgroundColor(bookActivity.resources.getColor(R.color.colorWhite))
        }else{
            holder.timeQuarter.setBackgroundColor(bookActivity.resources.getColor(R.color.colorPrimaryDark))
        }

        holder.timeQuarter.setOnClickListener {
            if (!timeDataList[position].allowed!!){
                bookActivity.selectedMakeTrue(position)
                holder.timeQuarter.setBackgroundColor(bookActivity.resources.getColor(R.color.colorWhite))
            }else{
                bookActivity.selectedMakeFalse(position)
                holder.timeQuarter.setBackgroundColor(bookActivity.resources.getColor(R.color.colorPrimaryDark))
            }

        }
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var timeTitle: TextView
        var timeQuarter: View

        init {
            timeTitle = itemView.findViewById(R.id.time_title)
            timeQuarter = itemView.findViewById(R.id.time_quarter)
        }
    }
}