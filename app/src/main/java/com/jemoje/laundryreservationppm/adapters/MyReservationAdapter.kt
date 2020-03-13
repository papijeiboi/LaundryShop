package com.jemoje.laundryreservationppm.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jemoje.laundryreservationppm.R
import com.jemoje.laundryreservationppm.fragment.MyReservationFragment
import com.jemoje.laundryreservationppm.model.MyReservationResponse

class MyReservationAdapter constructor(
    myReservationFrag: MyReservationFragment,
    myReservationData: MutableList<MyReservationResponse>
) : RecyclerView.Adapter<MyReservationAdapter.ItemViewHolder>() {

    private val myReservationDataList: MutableList<MyReservationResponse> = myReservationData
    private val myReservationFragment = myReservationFrag

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item_reservation, parent, false)


        return ItemViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return myReservationDataList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.reservationDate.text = myReservationDataList[position].reservedDate
        holder.reservationTime.text = "${myReservationDataList[position].reservedTimeIn} ${myReservationDataList[position].reservedTimeOut}"
        holder.reservationMachineNumber.text = myReservationDataList[position].machine!!.machineNumber
        holder.reservationShopName.text = myReservationDataList[position].shop!!.shopName
        holder.reservationAddress.text = myReservationDataList[position].shop!!.address
//
//        holder.itemView.setOnClickListener {
//            //            holder.itemView.startAnimation(AnimationUtils.loadAnimation(reservationFragment.activity, R.anim.image_click))
//
//
//            val intent = Intent(reservationFragment.activity, MachineActivity::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//            intent.putExtra("machine_id", reservationDataList[position].id)
//            reservationFragment.activity!!.applicationContext.startActivity(intent)
//            reservationFragment.activity!!.overridePendingTransition(
//                R.anim.enter_from,
//                R.anim.enter_to
//            )
//        }
    }


    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var reservationDate: TextView
        var reservationTime: TextView
        var reservationMachineNumber: TextView
        var reservationShopName: TextView
        var reservationAddress: TextView

        init {

            reservationDate = itemView.findViewById(R.id.tv_reservation_date)
            reservationTime = itemView.findViewById(R.id.tv_reservation_time)
            reservationMachineNumber = itemView.findViewById(R.id.tv_reservation_machine_number)
            reservationShopName = itemView.findViewById(R.id.tv_reservation_shop_name)
            reservationAddress = itemView.findViewById(R.id.tv_reservation_address)


        }
    }
}