package com.labs14tech2rent.tech2rent.activities

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import com.labs14tech2rent.tech2rent.R
import com.labs14tech2rent.tech2rent.adapters.DashboardRecyclerAdapterMain
import com.labs14tech2rent.tech2rent.models.Listing
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject


class MainActivity : BaseActivity() {

    val urlString = "http://labstech2rentstaging.herokuapp.com/api/items"
    val context = this
    var listings: MutableList<Listing> = mutableListOf()
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: DashboardRecyclerAdapterMain


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        nav_view.menu.getItem(0).isChecked = true




        //added uuid and userid, but they don't have a use in this activity as of yet.
        val sharedPrefs: SharedPreferences = getSharedPreferences("acct", Context.MODE_PRIVATE)
        val uuid = sharedPrefs.getString("uuid", "")
        val userid = sharedPrefs.getInt("userid", 0)

        val client = OkHttpClient()
        Thread(Runnable {
            val request: Request = Request.Builder().get()
                .url(urlString)
                .addHeader("Content-Type", "application/json;charset=UTF-8")
                .build()

            val response: Response = client.newCall(request).execute()


            val JSONstring: String? = response.body()?.string()
            val Jarray = JSONArray(JSONstring)


            for (i in 0 until Jarray.length()) {
                val item = Listing(Jarray.getJSONObject(i))
                listings.add(item)
            }








            for (listing in listings) {

                val itemUserId = listing.user_id

                val request2: Request = Request.Builder().get()
                    .url("http://labstech2rentstaging.herokuapp.com/api/users/$itemUserId/reviews")
                    .addHeader("Content-Type", "application/json;charset=UTF-8")
                    .build()

                val response2: Response = client.newCall(request2).execute()

                val JSONstring2 = response2.body()?.string()
                val profileJSON = JSONObject(JSONstring2)

                try {
                    listing.displayImage = Picasso.get().load(listing.picture_url).get()
                } catch (e: Exception) {
                }

                try {
                listing.profileImage = Picasso.get().load(profileJSON.getString("profile_picture")).get()
                } catch (e: Exception) {
                }
            }





            runOnUiThread {
                recyclerView = findViewById(R.id.recycler_view_main)
                recyclerView.setHasFixedSize(true)
                recyclerView.isNestedScrollingEnabled = false
                val layoutManager = LinearLayoutManager(this)
                recyclerView.layoutManager = layoutManager
                adapter = DashboardRecyclerAdapterMain(listings, context)
                recyclerView.adapter = adapter

            }
        }).start()


        button_filter_1.setOnClickListener {

            listings.sortBy {
                it.description
            }

            recyclerView.adapter = adapter
            adapter.notifyDataSetChanged()

            for (listing in adapter.dataList) {
                println(listing)

            }
        }


        button_filter_2.setOnClickListener {

            listings.sortBy {
                it.name
            }

            adapter.notifyDataSetChanged()

            for (listing in adapter.dataList) {
                println(listing)
            }

        }

        button_filter_3.setOnClickListener {

            listings.sortBy {
                it.condition
            }


            val adapter2 = DashboardRecyclerAdapterMain(listings, context)
            recyclerView.adapter = adapter2
            adapter2.notifyDataSetChanged()

            for (listing in adapter2.dataList) {
                println(listing)
            }
        }


        button_filter_4.setOnClickListener {

            listings.sortBy {
                it.zip_code
            }


            val adapter2 = DashboardRecyclerAdapterMain(listings, context)
            recyclerView.adapter = adapter2
            adapter2.notifyDataSetChanged()

            for(listing in adapter2.dataList) {
                println (listing)

            }

        }

    }



}
