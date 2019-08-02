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
import kotlinx.android.synthetic.main.activity_new_listing.*
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_profile.nav_view
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject

class ProfileActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        this.nav_view.menu.getItem(1).isChecked = true

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


        val sharedPrefs: SharedPreferences = getSharedPreferences("acct", Context.MODE_PRIVATE)
        val uuid = sharedPrefs.getString("uuid", "")
        val userid = sharedPrefs.getInt("userid", 0)
        val client: OkHttpClient = OkHttpClient()
        var profileJSON: JSONObject = JSONObject()
        var editableProfile: User = User("", "", "")


        /*
        *
        *  NETWORK GET USER REQUEST
        *
        * */
        Thread(Runnable {
            val request: Request = Request.Builder().get()
                .url("http://labstech2rentstaging.herokuapp.com/api/users/$userid/reviews")
                .addHeader("Content-Type", "application/json;charset=UTF-8")
                .build()

            val response: Response = client.newCall(request).execute()

            val JSONstring = response.body()?.string()

            runOnUiThread(Runnable {
                profileJSON = JSONObject(JSONstring)
                editableProfile = User(
                    uuid!!,
                    profileJSON.getString("email"),
                    profileJSON.getString("name"),
                    profileJSON.getString("profile_picture"),
                    profileJSON.getString("phone"),
                    profileJSON.getString("date_of_birth"),
                    profileJSON.getString("preferred_payment_type"),
                    profileJSON.getString("street"),
                    profileJSON.getString("city"),
                    profileJSON.getString("state"),
                    profileJSON.getInt("zip_code"),
                    profileJSON.getDouble("average_rating")
                )
                try {
                    Picasso.get().load(editableProfile.profile_picture).into(profileImage)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                editName.setText(editableProfile.name)
                editStreet.setText(editableProfile.street)
                editCity.setText(editableProfile.city)
                editState.setText(editableProfile.state)
                editZip.setText(editableProfile.zip_code.toString())
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
            })

        }).start()

        fun getPreferredPayment(): String {
            if (checkCash.isChecked && !checkCredit.isChecked) {
                return "cash"
            }
            if (checkCredit.isChecked && !checkCash.isChecked) {
                return "card"
            }
            return "both"
        }

        buttonSave.setOnClickListener(View.OnClickListener {
            val editedUser: User = User(
                uuid!!, editableProfile.email,
                editName.text.toString(),
                "profile pic",
                editPhone.text.toString(),
                editDOB.text.toString(),
                getPreferredPayment(),
                editStreet.text.toString(),
                editCity.text.toString(),
                editState.text.toString(),
                editZip.text.toString().toInt(),
                editableProfile.average_rating
            )


            val body = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                editedUser.toJSONString()
            )

            val postRequest = Request.Builder().url("https://labstech2rentstaging.herokuapp.com/api/users/$userid")
                .addHeader("Content-Type", "application/json;charset=UTF-8")
                .put(body).build()
            Thread(Runnable {
                val response = client.newCall(postRequest).execute()

                println(response.body()?.string())

            }).start()





        })


        buttonCancel.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(
                    context,
                    MainActivity::class.java
                )
            )
        })
    }
}


