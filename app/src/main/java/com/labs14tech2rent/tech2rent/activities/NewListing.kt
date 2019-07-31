package com.labs14tech2rent.tech2rent.activities

import android.os.Bundle
import com.labs14tech2rent.tech2rent.R
import com.labs14tech2rent.tech2rent.models.Listing
import kotlinx.android.synthetic.main.activity_new_listing.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray

class NewListing : BaseActivity() {

    private val urlString = "https://labstech2rentstaging.herokuapp.com/api/users/1/items"

    companion object {
        val JSON = "application/json; charset=utf-8".toMediaTypeOrNull()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_listing)
        this.nav_view.menu.getItem(2).isChecked = true



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

            var zipcode = ""
            var city = ""
            var state = ""

            if (listing_location.text.toString().substringAfter(',').contains(',')) {

                zipcode = listing_location.text.toString().substringAfterLast(',')
                city = listing_location.text.toString().substringBefore(',')
                state = listing_location.text.toString().substringAfter(',').substringBefore(',')
            }


            val listing = Listing(0,1, //TODO: change user_id here once authorization is functional
                listing_title.text.toString(),
                listing_price.text.toString(), "",
                listing_category.text.toString(),
                listing_description.text.toString(), true,
                payments, "5",
                listing_condition_spinner.selectedItem.toString(),
                listing_sub_category.text.toString(),
                city,
                state,
                zipcode
                )


            Thread(Runnable {
                val client = OkHttpClient()
                val body: RequestBody = listing.toString().toRequestBody(JSON)
                val request: Request = Request.Builder()
                    .url(urlString)
                    .post(body)
                    .build()

                try {
                    val response = client.newCall(request).execute()
                    val result = response.body?.string()
                    val resultJSON: JSONArray = JSONArray(result)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }).start()

        }
    }
}
