package com.labs14tech2rent.tech2rent.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedListAdapterCallback
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter
import com.labs14tech2rent.tech2rent.R
import com.labs14tech2rent.tech2rent.adapters.DashboardRecyclerAdapterMain
import com.labs14tech2rent.tech2rent.adapters.ListingAdapter
import com.labs14tech2rent.tech2rent.databinding.ActivityMainBinding
import com.labs14tech2rent.tech2rent.models.Listing
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray


class MainActivity : BaseActivity(), SearchView.OnQueryTextListener, SortedListAdapter.Callback{



    val urlString = "http://labstech2rentstaging.herokuapp.com/api/items"
    val context = this

    val comparator: Comparator<Listing> = SortedListAdapter.ComparatorBuilder<Listing>()
        .build()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        nav_view.menu.getItem(0).isChecked = true

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val listingAdapter = ListingAdapter(this, comparator, model -> {


        })


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
            val listings: ArrayList<Listing> = ArrayList()
            val JSONstring: String? = response.body()?.string()
            val Jarray = JSONArray(JSONstring)


            for (i in 0 until Jarray.length()) {
                val item = Listing(Jarray.getJSONObject(i))
                listings.add(item)
            }

            runOnUiThread {
                val recyclerView: RecyclerView = findViewById(R.id.recycler_view_main)
                recyclerView.setHasFixedSize(true)
                recyclerView.isNestedScrollingEnabled = false
                val layoutManager = LinearLayoutManager(this)
                recyclerView.layoutManager = layoutManager
                val adapter = DashboardRecyclerAdapterMain(listings, context)
                recyclerView.adapter = adapter
            }

        }).start()
    }


    override fun onQueryTextSubmit(query: String?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onEditFinished() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onEditStarted() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
