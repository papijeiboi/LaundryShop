package com.jemoje.laundryreservationppm.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.jemoje.laundryreservationppm.R
import com.jemoje.laundryreservationppm.activities.BookActivity
import com.jemoje.laundryreservationppm.activities.MachineActivity
import com.jemoje.laundryreservationppm.model.MachineResponseData

class MachineAdapter constructor(
    machineAct: MachineActivity,
    machineData: MutableList<MachineResponseData>
) : RecyclerView.Adapter<MachineAdapter.ItemViewHolder>() {

    private val machineDataList: MutableList<MachineResponseData> = machineData
    private val machineActivity = machineAct

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item_machine, parent, false)


        return ItemViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return machineDataList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.machineNumber.text = machineDataList[position].machineNumber


        holder.machineLayout.setOnClickListener {
            holder.itemView.startAnimation(
                AnimationUtils.loadAnimation(
                    machineActivity,
                    R.anim.image_click
                )
            )

            val machineDataList = machineDataList[position]
            val gson = Gson()
            val machineString = gson.toJson(machineDataList)

            val intent = Intent(machineActivity, BookActivity::class.java)
            intent.putExtra("machine_data_book", machineString)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            machineActivity.applicationContext.startActivity(intent)
            machineActivity.overridePendingTransition(R.anim.enter_from, R.anim.enter_to)
        }
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        var machineNumber: TextView
        var machineLayout: CardView

        init {
            machineNumber = itemView.findViewById(R.id.tv_machine_number)
            machineLayout = itemView.findViewById(R.id.machine_layout)

        }
    }

//    private fun getTotalReservationForToday():Int{
//        machineDataList[0].reservations!!.forEach {
//            it.reservedDate
//        }
//    }
}