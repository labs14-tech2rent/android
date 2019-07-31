package com.labs14tech2rent.tech2rent.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import com.labs14tech2rent.tech2rent.R
import kotlinx.android.synthetic.main.activity_register.*

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


    }
}
