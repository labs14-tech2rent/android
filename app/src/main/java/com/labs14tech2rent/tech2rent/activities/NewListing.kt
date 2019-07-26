package com.labs14tech2rent.tech2rent.activities

import android.os.Bundle
import android.view.View
import com.labs14tech2rent.tech2rent.R
import com.labs14tech2rent.tech2rent.models.Listing
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_new_listing.*
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray

class NewListing : BaseActivity() {

    private val urlString = "https://labstech2rentstaging.herokuapp.com/test"

    companion object {
        val JSON = "application/json; charset=utf-8".toMediaTypeOrNull()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_listing)


        val listing = Listing("", "Android Test Listing", "Best camera around", "50.00", "France", "Nikon", "card")

        submit_button.setOnClickListener {


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
                    textView.text = resultJSON.get(1).toString()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }).start()

        }
    }
}
