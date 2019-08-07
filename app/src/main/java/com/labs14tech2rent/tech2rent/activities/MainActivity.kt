package com.labs14tech2rent.tech2rent.activities

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.labs14tech2rent.tech2rent.R
import com.labs14tech2rent.tech2rent.models.Listing
import com.labs14tech2rent.tech2rent.models.User
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.nav_view
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject



class MainActivity : BaseActivity() {

    val urlString = "http://labstech2rentstaging.herokuapp.com/api/items"


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
            val listings: ArrayList<Listing> = ArrayList()
            val JSONstring: String? = response.body()?.string()
            val Jarray = JSONArray(JSONstring)


            for (i in 0 until Jarray.length()) {
                val item = Listing(Jarray.getJSONObject(i))
                listings.add(item)
            }

        }).start()
    }
}
