package com.labs14tech2rent.tech2rent.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.labs14tech2rent.tech2rent.R
import com.labs14tech2rent.tech2rent.models.User
import kotlinx.android.synthetic.main.activity_register.*
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject


class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val editName: EditText = edit_full_name
        val editTitle: EditText = edit_title
        val editStreet: EditText = edit_street_address
        val editCity: EditText = edit_city
        val editState: EditText = edit_state
        val editZip: EditText = edit_zip
        val editPhone: EditText = edit_phone
        val editDob: EditText = edit_dob
        val editEmail: EditText = edit_email
        val buttonFinish: ImageButton = button_finish

        val sharedPrefs: SharedPreferences = getSharedPreferences("acct", Context.MODE_PRIVATE)
        val uuid = sharedPrefs.getString("uuid", "")
        val client: OkHttpClient = OkHttpClient()
        val context = this


        buttonFinish.setOnClickListener(View.OnClickListener {
            val newUser = User(uuid!!,
                editEmail.text.toString(),
                editName.text.toString(),
                "",
                editPhone.text.toString(),
                editDob.text.toString(),
                "both",
                editStreet.text.toString(),
                editCity.text.toString(),
                editState.text.toString(),
                editZip.text.toString().toInt(),
                0.0,
                "",
                editTitle.text.toString())


            Thread(Runnable {

            val body = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                newUser.toJSONString()
            )

            val request: Request = Request.Builder().post(body)
                .url("http://labstech2rentstaging.herokuapp.com/api/auth/register")
                .addHeader("Content-Type", "application/json;charset=UTF-8")
                .build()

            val response: Response = client.newCall(request).execute()

            val JSON = response.body()?.string()

                if(JSON!!.contains("Created")){
                    val body2 = RequestBody.create(
                        MediaType.parse("application/json; charset=utf-8"),
                        "{ \"auth0_user_id\": \"$uuid\" }"
                    )

                    val request2: Request = Request.Builder().post(body)
                        .url("http://labstech2rentstaging.herokuapp.com/api/users/finduser")
                        .addHeader("Content-Type", "application/json;charset=UTF-8")
                        .build()

                    println(body.toString())
                    val response2: Response = client.newCall(request).execute()


                    val JSONstring = response.body()?.string()

                    val JSONArray = JSONArray(JSONstring)
                    val JSON2 = JSONArray.get(0) as JSONObject
                    val userId = JSON2.getInt("id")
                    val editor = sharedPrefs.edit()
                    editor.putInt("userid", userId)
                    editor.apply()
                    Toast.makeText(context,
                        "Successfully logged in",
                        Toast.LENGTH_SHORT).show()

                    val intent = Intent(context, MainActivity::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(context,
                        "Could not create account.Please try again",
                        Toast.LENGTH_SHORT).show()
                }

            println(JSON)

            }).start()
        })

    }
}
