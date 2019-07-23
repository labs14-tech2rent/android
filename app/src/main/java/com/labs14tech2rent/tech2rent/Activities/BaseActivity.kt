package com.labs14tech2rent.tech2rent.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import kotlinx.android.synthetic.main.activity_base.*

class BaseActivity : AppCompatActivity() {

    override fun onStart() {
        super.onStart()
        val toolbar: Toolbar = tool_bar as Toolbar
        setSupportActionBar(toolbar)
        toolbar.title = title

        val drawerLayout: DrawerLayout = drawer_layout as DrawerLayout

    }
}
