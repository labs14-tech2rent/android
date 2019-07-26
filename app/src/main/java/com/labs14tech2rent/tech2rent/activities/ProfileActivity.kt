package com.labs14tech2rent.tech2rent.Activities

import Models.User
import android.os.Bundle
import android.widget.CheckBox
import android.widget.EditText
import com.labs14tech2rent.tech2rent.R
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val profileImage: CircleImageView = image_profile_picture
        val editName: EditText = edit_name
        val editStreet: EditText = edit_street_address
        val editCity: EditText = edit_city
        val editState: EditText = edit_state
        val editZip: EditText = edit_zip
        val editDOB: EditText = edit_dob
        val editPhone: EditText = edit_phone
        val checkCash: CheckBox = check_preferred_cash
        val checkCredit: CheckBox = check_preferred_credit

        /*
        *
        *  NETWORK GET USER REQUEST
        *
        * */


        Picasso.get().load("").into(profileImage)
        editName.setText("")
        editStreet.setText("")
        editCity.setText("")
        editState.setText("")
        editZip.setText("")
        editDOB.setText("")
        editPhone.setText("")
        if(true) {
            checkCash.isChecked = false
        }
        if(true) {
            checkCredit.isChecked = false
        }

    }
}


fun saveProfile(user: User, id: Int){
    /*
    * SAVE USER FUNCTIONALITY
    * */
}