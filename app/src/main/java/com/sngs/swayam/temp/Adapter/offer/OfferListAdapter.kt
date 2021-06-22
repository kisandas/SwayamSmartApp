package com.sngs.swayam.temp.Adapter.offer

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sngs.swayam.temp.Activity.offer.OfferDetailActivity
import com.sngs.swayam.temp.Network.model.PromotionList.PromotionListResult
import com.sngs.swayam.temp.R
import kotlinx.android.synthetic.main.offer_item_layout.view.*


class OfferListAdapter(var arrayList: List<PromotionListResult>, private val context: Context) :
    RecyclerView.Adapter<OfferListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.offer_item_layout, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide.with(context).load(arrayList.get(position).promotionAttachment)
                .placeholder(R.drawable.app_logo).into(holder.itemView.offer_img);

      /*  holder.itemView.product_txt.setText(""+arrayList.get(position).promotionAdditionalOffer+" Off @")
        holder.itemView.coins.setText(""+arrayList.get(position).promotionPrice)*/

        holder.itemView.product_txt.setText("Flat Rs. "+arrayList.get(position).getmPromotionFinalRate())
        holder.itemView.coins.setText("Get for 20 Coins")

        holder.itemView.offer_item_layout.setOnClickListener {
            val intent = Intent(context, OfferDetailActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra("selected_pos",""+position)
            context.startActivity(intent)
        }
    }

}