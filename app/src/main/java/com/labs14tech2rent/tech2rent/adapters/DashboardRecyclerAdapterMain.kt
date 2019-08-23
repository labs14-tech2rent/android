package com.labs14tech2rent.tech2rent.adapters

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.labs14tech2rent.tech2rent.activities.DetailsActivity
import com.labs14tech2rent.tech2rent.Fragments.ViewProfileFragment
import com.labs14tech2rent.tech2rent.R
import com.labs14tech2rent.tech2rent.models.Listing
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.listing_single_item.view.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject

class DashboardRecyclerAdapterMain(var dataList: List<Listing>, val activity: AppCompatActivity) :
    RecyclerView.Adapter<DashboardRecyclerAdapterMain.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val listingImagePreview: ImageView = view.single_item_imageview
        val textListingTitle: TextView = view.single_item_title_textview
        val textListingDescription: TextView = view.single_item_description_textview
        val textListingPrice: TextView = view.single_item_price_textview
        val textListingLocation: TextView = view.single_item_location_textview
        val listingImageProfile: ImageView = view.single_item_profile_pic_imageview
        val parent: View = view.parent_layout


        fun bindModel(listing: Listing, activity: AppCompatActivity) {

            if (listing.name.length > 37) { //length at which a substring is used
                val temp: String = listing.name.substring(0,34) + "..."
                textListingTitle.text = temp
            } else {
                textListingTitle.text = listing.name
            }
            if (listing.description.length > 75) {
                val temp: String = listing.description.substring(0,70) + "..."
                textListingDescription.text = temp
            } else {
                textListingDescription.text = listing.description
            }
            textListingPrice.text = listing.listing_price
            Thread(Runnable {

                if (listing.displayImage != null) {
                    try {
                        listingImagePreview.setImageBitmap(listing.displayImage)
                    } catch (e: Exception) {
                    }

                }

                val client: OkHttpClient = OkHttpClient()
                val userid = listing.user_id



                if (listing.profileImage != null) {
                    try {

                        listingImageProfile.setImageBitmap(listing.profileImage)

                    } catch (e: Exception) {
                    }
                }
            }).start()


            listingImageProfile.setOnClickListener(View.OnClickListener {
                val profileFragment = ViewProfileFragment()
                val args = Bundle()
                args.putParcelable("listing", listing)
                profileFragment.arguments = args
                profileFragment.show(activity.supportFragmentManager, "profile")
            })


            parent.setOnClickListener(View.OnClickListener {
                /*
                * Transfer to item details page
                * */
                val intent = Intent(itemView.context, DetailsActivity::class.java)
                /*val bundle: Bundle = Bundle()
                bundle.putParcelable("listing", listing)*/
                val listingString = listing.toTransferString()

                intent.putExtra("listing", listingString)
//                intent.putExtras(bundle)

                itemView.context.startActivity(intent)
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
        holder.bindModel(data, activity)
    }
}