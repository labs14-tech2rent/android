package com.labs14tech2rent.tech2rent.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.labs14tech2rent.tech2rent.R
import kotlinx.android.synthetic.main.activity_new_listing.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        this.nav_view.menu.getItem(6).isChecked = true
    }
}
