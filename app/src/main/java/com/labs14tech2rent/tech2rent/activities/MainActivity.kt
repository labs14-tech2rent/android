package com.labs14tech2rent.tech2rent.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.labs14tech2rent.tech2rent.R
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray

class MainActivity : AppCompatActivity() {

    val urlString = "https://labstech2rentstaging.herokuapp.com/test"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonGet: Button = button_get

        val client: OkHttpClient = OkHttpClient()

        buttonGet.setOnClickListener(View.OnClickListener {

            Thread(Runnable {
            val request: Request = Request.Builder().url(urlString).build()

            try{
                val response = client.newCall(request).execute()
                val result = response.body?.string()
                val resultJSON: JSONArray = JSONArray(result)
                textView.text = resultJSON.get(1).toString()
            }catch (e: Exception){
                e.printStackTrace()
            }
            }).start()
        })
    }
}
