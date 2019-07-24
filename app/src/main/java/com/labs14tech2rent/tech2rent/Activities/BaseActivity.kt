package com.labs14tech2rent.tech2rent.Activities

import android.app.Dialog
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.provider.AuthCallback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.google.android.material.navigation.NavigationView
import com.labs14tech2rent.tech2rent.R
import kotlinx.android.synthetic.main.activity_base.*

abstract class BaseActivity : AppCompatActivity() {

    override fun onStart() {
        super.onStart()
        val context = this
        val toolbar: Toolbar = tool_bar as Toolbar
        setSupportActionBar(toolbar)
        toolbar.title = title

        val drawerLayout = drawer_layout
        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val navView: NavigationView = nav_view

        val account: Auth0 = Auth0(context)
        account.isOIDCConformant = true


        navView.setNavigationItemSelectedListener {it ->
            when(it.itemId){
                R.id.nav_login -> {
                    WebAuthProvider.login(account).withScheme("https")
                        .withAudience(String.format("https://%s/userinfo", getString(R.string.com_auth0_domain)))
                        .start(this, object: AuthCallback{
                            override fun onSuccess(credentials: Credentials) {
                                println(credentials)
                            }

                            override fun onFailure(dialog: Dialog) {
                                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                            }

                            override fun onFailure(exception: AuthenticationException?) {
                                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                            }

                        })

                }
            }
            drawerLayout.closeDrawers()
            true
        }
    }
}
