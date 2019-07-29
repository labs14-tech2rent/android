package com.labs14tech2rent.tech2rent.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import com.labs14tech2rent.tech2rent.R
import com.labs14tech2rent.tech2rent.models.User
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_profile.*
import okhttp3.*
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
        val buttonSave: Button = button_done
        val buttonCancel: Button = button_cancel
        val context = this

        /*
        *
        *  NETWORK GET USER REQUEST
        *
        * */
        val client: OkHttpClient = OkHttpClient()
        var profileString: String = ""
        val sharedPrefs: SharedPreferences = getSharedPreferences("acct", Context.MODE_PRIVATE)
        val uuid = sharedPrefs.getString("uuid", "")
        val userid = 1
        Thread(Runnable {
            val request: Request =
                Request.Builder().url("https://labstech2rentstaging.herokuapp.com/api/users/$userid/reviews").build()
            try {
                var response: Response = client.newCall(request).execute()
                profileString = response.body!!.string()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }).start()

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

        fun getPreferredPayment(): String{
            if (checkCash.isChecked && !checkCredit.isChecked){
                return "cash"
            }
            if (checkCredit.isChecked && !checkCash.isChecked){
                return "card"
            }
            return "both"
        }

        buttonSave.setOnClickListener(View.OnClickListener {
/*            val editedUser: User = User(uuid!!, editableProfile.email,
                editName.text.toString(),
                "profile pic",
                editPhone.text.toString(),
                editDOB.text.toString(),
                getPreferredPayment(),
                editStreet.text.toString(),
                editCity.text.toString(),
                editState.text.toString(),
                editZip.text.toString().toInt(),
                editableProfile.average_rating)*/



            val body = FormBody.Builder()
                .add("auth0_user_id", uuid)
                .add("email", editableProfile.email)
                .add("profile_picture", "profile picture")
                .add("phone", editPhone.text.toString())
                .add("date_of_birth", editDOB.text.toString())
                .add("preferred_payment_type", getPreferredPayment())
                .add("street", editStreet.text.toString())
                .add("city", editCity.text.toString())
                .add("state", editState.text.toString())
                .add("zip_code", editZip.text.toString())
                .build()

            val postRequest = Request.Builder().url("http://labstech2rent.herokuapp.com/api/users/$userid")
                .put(body).build()

            val response = client.newCall(postRequest).execute()



        })


        buttonCancel.setOnClickListener(View.OnClickListener {
            startActivity(Intent(
            context,
            MainActivity::class.java)
        )})
    }
}


