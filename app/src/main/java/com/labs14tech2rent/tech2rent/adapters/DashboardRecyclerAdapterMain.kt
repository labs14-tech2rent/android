package com.labs14tech2rent.tech2rent.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.labs14tech2rent.tech2rent.R
import com.labs14tech2rent.tech2rent.models.Listing
import kotlinx.android.synthetic.main.listing_single_item.view.*

class DashboardRecyclerAdapterMain(val dataList: List<Listing>): RecyclerView.Adapter<DashboardRecyclerAdapterMain.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val listingImagePreview: ImageView = view.single_item_imageview
        val textListingTitle: TextView = view.single_item_title_textview
        val textListingDescription: TextView = view.single_item_description_textview
        val textListingPrice: TextView = view.single_item_price_textview
        val textListingLocation: TextView = view.single_item_location_textview
        val listingImageProfile: ImageView = view.single_item_profile_pic_imageview


        fun bindModel(listing: Listing){

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardRecyclerAdapterMain.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.listing_single_item, parent, false))
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: DashboardRecyclerAdapterMain.ViewHolder, position: Int) {
        val data: Listing = dataList.get(position)
        holder.bindModel(data)
    }
}