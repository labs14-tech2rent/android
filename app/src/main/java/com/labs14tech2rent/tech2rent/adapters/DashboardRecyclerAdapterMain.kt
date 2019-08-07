package com.labs14tech2rent.tech2rent.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.labs14tech2rent.tech2rent.R
import com.labs14tech2rent.tech2rent.models.Listing
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.listing_single_item.view.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject

class DashboardRecyclerAdapterMain(val dataList: List<Listing>): RecyclerView.Adapter<DashboardRecyclerAdapterMain.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val listingImagePreview: ImageView = view.single_item_imageview
        val textListingTitle: TextView = view.single_item_title_textview
        val textListingDescription: TextView = view.single_item_description_textview
        val textListingPrice: TextView = view.single_item_price_textview
        val textListingLocation: TextView = view.single_item_location_textview
        val listingImageProfile: ImageView = view.single_item_profile_pic_imageview
        val parent: View = view.parent_layout


        fun bindModel(listing: Listing){

            //textListingLocation.text =
            textListingTitle.text = listing.name
            textListingDescription.text = listing.description
            textListingPrice.text = listing.listing_price
            Thread(Runnable {
                try{
                    listingImagePreview.setImageBitmap(Picasso.get().load(listing.picture_url).get())
                }catch (e: Exception){}

                val client: OkHttpClient = OkHttpClient()
                val userid = listing.user_id

                val request: Request = Request.Builder().get()
                    .url("http://labstech2rentstaging.herokuapp.com/api/users/$userid/reviews")
                    .addHeader("Content-Type", "application/json;charset=UTF-8")
                    .build()

                val response: Response = client.newCall(request).execute()

                val JSONstring = response.body()?.string()
                val profileJSON = JSONObject(JSONstring)

                try {
                    listingImageProfile.setImageBitmap(Picasso.get().load(profileJSON.getString("profile_picture")).get())
                }catch (e: Exception){}
            }).start()


            parent.setOnClickListener(View.OnClickListener {
                /*
                * Transfer to item details page
                * */
            })
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