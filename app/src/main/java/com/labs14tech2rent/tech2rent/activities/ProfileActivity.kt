package com.labs14tech2rent.tech2rent.Activities

import Models.User
import android.os.Bundle
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.EditText
import com.labs14tech2rent.tech2rent.R
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_profile.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject

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
        val client: OkHttpClient = OkHttpClient()
        var profileString: String = ""
        val userid = 1
        val request: Request =
            Request.Builder().url("https://labstech2rentstaging.herokuapp.com/api/users/$userid/reviews").build()
        try {
            var response: Response = client.newCall(request).execute()
            profileString = response.body!!.string()

        } catch (e: Exception) {
            e.printStackTrace()
        }
        val profileJSON: JSONObject = JSONObject(profileString)
        var editableProfile: User = User(
            profileJSON.getString("auth0_user_id"),
            profileJSON.getString("email"),
            profileJSON.getString("name"),
            profileJSON.getString("profile_picture"),
            profileJSON.getString("phone"),
            profileJSON.getString("date_of_birth"),
            profileJSON.getString("preferred_payment"),
            profileJSON.getString("street"),
            profileJSON.getString("city"),
            profileJSON.getString("state"),
            profileJSON.getInt("zip_code"),
            profileJSON.getDouble("average_rating")
        )
        Picasso.get().load(editableProfile.profile_picture).into(profileImage)
        editName.setText(editableProfile.name)
        editStreet.setText(editableProfile.street)
        editCity.setText(editableProfile.city)
        editState.setText(editableProfile.state)
        editZip.setText(editableProfile.zip_code)
        editDOB.setText(editableProfile.date_of_birth)
        editPhone.setText(editableProfile.phone)
        when (editableProfile.preferred_payment_type) {
            "cash" -> checkCash.isChecked = true
            "card" -> checkCredit.isChecked = true
            "both" -> {
                checkCash.isChecked = true
                checkCredit.isChecked = true
            }
        }


    }


    fun saveProfile(user: User, id: Int) {
        /*
        * SAVE USER FUNCTIONALITY
        * */
    }
}


