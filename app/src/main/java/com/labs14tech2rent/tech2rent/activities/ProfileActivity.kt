package com.labs14tech2rent.tech2rent.Activities

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.labs14tech2rent.tech2rent.R
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val profileImage: ImageView = image_profile_picture
        val profileName: TextView = text_profile_name
        val buttonEditName: ImageButton = button_edit_profile_name
        val textDob: TextView = text_dob_value
        val buttonEditDob: ImageButton = button_edit_dob
        val textPhone: TextView = text_phone_value
        val buttonEditPhone: ImageButton = button_edit_phone
        val textPayment: TextView = text_payment_value
        val buttonEditPayment: ImageButton = button_edit_payment
        val textAddress: TextView = text_address_value
        val buttonEditAddress: ImageButton = button_edit_address
        val textRating: TextView = text_rating_value
        val buttonListings: Button = button_my_listings


    }
}
