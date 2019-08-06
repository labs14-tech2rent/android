package com.labs14tech2rent.tech2rent.activities

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import com.labs14tech2rent.tech2rent.R
import com.labs14tech2rent.tech2rent.models.Listing
import kotlinx.android.synthetic.main.activity_new_listing.*
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONArray

class NewListing : BaseActivity() {



    companion object {
        val JSON = MediaType.parse("application/json; charset=utf-8")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_listing)
        this.nav_view.menu.getItem(2).isChecked = true


        val sharedPrefs: SharedPreferences = getSharedPreferences("acct", Context.MODE_PRIVATE)
        val uuid = sharedPrefs.getString("uuid", "")
        val userid = sharedPrefs.getInt("userid", 0)
        val urlString = "https://labstech2rentstaging.herokuapp.com/api/users/$userid/items"



        submit_button.setOnClickListener {

            val cash = listing_checkbox_cash.isChecked
            val card = listing_checkbox_card.isChecked

            val payments = if (card && cash) {
                "Card or Cash"
            } else if (cash) {
                "Cash"
            } else if (card) {
                "Card"
            } else {
                "none"
            }





            val listing = Listing(0,userid,
                listing_title.text.toString(),
                listing_price.text.toString(), "",
                listing_category.text.toString(),
                listing_description.text.toString(), true,
                payments, "5",
                listing_condition_spinner.selectedItem.toString(),
                listing_sub_category.text.toString(),
                listing_city.text.toString(),
                listing_state.text.toString(),
                listing_zip.text.toString()
                )


            Thread(Runnable {
                val client = OkHttpClient()
                val body: RequestBody = RequestBody.create(JSON, listing.toString())
                val request: Request = Request.Builder()
                    .url(urlString)
                    .post(body)
                    .addHeader("Content-Type", "application/json;charset=UTF-8")
                    .build()




                try {
                    val response = client.newCall(request).execute()
                    val result = response.body()?.string()
                    val resultJSON: JSONArray = JSONArray(result)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }).start()

        }
    }
}
