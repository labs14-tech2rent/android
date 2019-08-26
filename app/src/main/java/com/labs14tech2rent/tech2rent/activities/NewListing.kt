package com.labs14tech2rent.tech2rent.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import com.amazonaws.mobile.client.AWSMobileClient
import com.labs14tech2rent.tech2rent.R
import com.labs14tech2rent.tech2rent.models.Listing
import kotlinx.android.synthetic.main.activity_new_listing.*
import org.jetbrains.anko.toast
import org.json.JSONArray
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver
import java.io.File.separator
import android.os.Environment.getExternalStorageDirectory
import android.R.attr.bitmap
import android.os.Environment
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState
import okhttp3.*
import java.io.*


class NewListing : BaseActivity() {



    companion object {
        val JSON = MediaType.parse("application/json; charset=utf-8")
    }

    var imageBitmap: Bitmap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_listing)
        this.nav_view.menu.getItem(2).isChecked = true


        val context = this
        val client = OkHttpClient()

        val sharedPrefs: SharedPreferences = getSharedPreferences("acct", Context.MODE_PRIVATE)
        val uuid = sharedPrefs.getString("uuid", "")
        val userid = sharedPrefs.getInt("userid", 0)
        val urlString = "https://labstech2rentstaging.herokuapp.com/api/users/$userid/items"


        button_add_image.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.resolveActivity(packageManager)
            startActivityForResult(intent, 210)
        }

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





                val body: RequestBody = RequestBody.create(JSON, listing.toString())
                val request: Request = Request.Builder()
                    .url(urlString)
                    .post(body)
                    .addHeader("Content-Type", "application/json;charset=UTF-8")
                    .build()




                try {
                    val response = client.newCall(request).execute()
                    val result = response.body()?.string()
                    println(result)
                    //val resultJSON: JSONArray = JSONArray(result)
                    val intent = Intent(context, MainActivity::class.java)
                    runOnUiThread { toast("Success") }
                    startActivity(intent)

                } catch (e: Exception) {
                    runOnUiThread { toast("Error, check fields and try again") }
                    e.printStackTrace()
                }
            }).start()

        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == 210 && resultCode == Activity.RESULT_OK){
            imageBitmap = data?.extras?.get("data") as Bitmap
            new_listing_imageview.setImageBitmap(imageBitmap)
        }
    }


}
