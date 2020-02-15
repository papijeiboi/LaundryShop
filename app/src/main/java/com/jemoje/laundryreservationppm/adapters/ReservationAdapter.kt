package com.jemoje.laundryreservationppm.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.jemoje.laundryreservationppm.R
import com.jemoje.laundryreservationppm.activities.MachineActivity
import com.jemoje.laundryreservationppm.fragment.ReservationFragment
import com.jemoje.laundryreservationppm.model.ReservationResponseData

class ReservationAdapter constructor(
    reservationFrag: ReservationFragment,
    reservationData: MutableList<ReservationResponseData>
) : RecyclerView.Adapter<ReservationAdapter.ItemViewHolder>() {

    private val reservationDataList: MutableList<ReservationResponseData> = reservationData
    private val reservationFragment = reservationFrag

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item_shops, parent, false)


        return ItemViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return reservationDataList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.shopName.text = reservationDataList[position].shopName
        holder.shopAddress.text = reservationDataList[position].address
        holder.shopPrice.text = reservationDataList[position].price
        holder.priceType.text = reservationDataList[position].priceType
        holder.shopsOpening.text = reservationDataList[position].openingTime
        holder.shopsClosing.text = reservationDataList[position].closingTime
        holder.shopsStartDay.text = reservationDataList[position].startDay
        holder.shopsEndDay.text = reservationDataList[position].endDay

        holder.itemView.setOnClickListener {
            //            holder.itemView.startAnimation(AnimationUtils.loadAnimation(reservationFragment.activity, R.anim.image_click))


            val intent = Intent(reservationFragment.activity, MachineActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra("machine_id", reservationDataList[position].id)
            reservationFragment.activity!!.applicationContext.startActivity(intent)
            reservationFragment.activity!!.overridePendingTransition(
                R.anim.enter_from,
                R.anim.enter_to
            )
        }
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        var shopName: TextView
        var shopAddress: TextView
        var shopPrice: TextView
        var priceType: TextView
        var shopsOpening: TextView
        var shopsClosing: TextView
        var shopsStartDay: TextView
        var shopsEndDay: TextView

        init {

            shopName = itemView.findViewById(R.id.tv_shops_shop_name)
            shopAddress = itemView.findViewById(R.id.tv_shops_address)
            shopPrice = itemView.findViewById(R.id.tv_shops_price)
            priceType = itemView.findViewById(R.id.tv_shops_price_type)
            shopsOpening = itemView.findViewById(R.id.tv_shops_opening)
            shopsClosing = itemView.findViewById(R.id.tv_shops_closing)
            shopsStartDay = itemView.findViewById(R.id.tv_shops_start_day)
            shopsEndDay = itemView.findViewById(R.id.tv_shops_end_day)


        }
    }
}