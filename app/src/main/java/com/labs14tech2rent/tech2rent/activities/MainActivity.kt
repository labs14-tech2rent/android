package com.labs14tech2rent.tech2rent.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.View
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
import androidx.databinding.ViewDataBinding
import java.util.Locale.filter


class MainActivity : BaseActivity(), SearchView.OnQueryTextListener, SortedListAdapter.Callback{



    val urlString = "http://labstech2rentstaging.herokuapp.com/api/items"
    val context = this
    val listings: ArrayList<Listing> = ArrayList()
    lateinit var mAdapter : ListingAdapter
    private val COMPARATOR = SortedListAdapter.ComparatorBuilder<Listing>()
        .setOrderForModel(Listing::class.java) { a, b -> Integer.signum(b.name.compareTo(a.name)) }
        .build()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        nav_view.menu.getItem(0).isChecked = true
        val linearLayoutManager = LinearLayoutManager(this)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.recyclerViewMain.layoutManager = linearLayoutManager

        mAdapter = ListingAdapter(this, COMPARATOR, ListingAdapter.Listener {
            //THIS SEEMS TO BE THE ON CLICK RESULTING FUNCTION
        })

        mAdapter.addCallback(this)
        binding.recyclerViewMain.adapter = mAdapter




        search_edittext.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null) {
                    if(s.isNotEmpty()) {
                        val query = s.toString()
                        val filteredListings = filter(listings, query)
                        mAdapter.edit().removeAll().replaceAll(filteredListings).commit()
                    }
                }
            }

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

            val JSONstring: String? = response.body()?.string()
            val Jarray = JSONArray(JSONstring)


            for (i in 0 until Jarray.length()) {
                val item = Listing(Jarray.getJSONObject(i))
                listings.add(item)
            }

            runOnUiThread {
                //Command to update the list
                mAdapter.edit().replaceAll(listings).commit()
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

    override fun onQueryTextChange(query: String): Boolean {
        val filteredList: List<Listing> = filter(listings, query)
        mAdapter.edit().replaceAll(listings).commit()
        return true
    }

    override fun onEditFinished() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onEditStarted() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun filter(listings: List<Listing>, query: String): List<Listing> {

        val lowerCaseQuery = query.toLowerCase()
        val filteredModeList: ArrayList<Listing> = ArrayList()

        for (item in listings) {
            val name = item.name.toLowerCase()
            val description = item.description.toLowerCase()

            if (name.contains(lowerCaseQuery) || description.contains(lowerCaseQuery)) {
                filteredModeList.add(item)

            }
        }

        return filteredModeList
    }
}
