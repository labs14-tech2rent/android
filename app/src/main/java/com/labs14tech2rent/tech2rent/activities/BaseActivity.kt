package com.labs14tech2rent.tech2rent.Activities

import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.labs14tech2rent.tech2rent.R
import kotlinx.android.synthetic.main.activity_base.*

class BaseActivity : AppCompatActivity() {

    override fun onStart() {
        super.onStart()
        val toolbar: Toolbar = tool_bar as Toolbar
        setSupportActionBar(toolbar)
        toolbar.title = title

        val drawerLayout: DrawerLayout = drawer_layout as DrawerLayout
        val ActionBarToggle: ActionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(ActionBarToggle)
        ActionBarToggle.syncState()

        val navView: NavigationView = nav_view

        navView.setNavigationItemSelectedListener {it ->
            when(it.itemId){

            }
            drawerLayout.closeDrawers()
            true
        }
    }
}
