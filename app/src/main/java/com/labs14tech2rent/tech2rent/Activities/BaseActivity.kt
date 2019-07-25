package com.labs14tech2rent.tech2rent.Activities

import android.app.Dialog
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.BaseCallback
import com.auth0.android.management.UsersAPIClient
import com.auth0.android.provider.AuthCallback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.auth0.android.result.UserProfile
import com.google.android.material.navigation.NavigationView
import com.labs14tech2rent.tech2rent.R
import kotlinx.android.synthetic.main.activity_base.*
import com.auth0.android.management.ManagementException



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
        //account.isOIDCConformant = true


        navView.setNavigationItemSelectedListener {it ->
            when(it.itemId){
                com.labs14tech2rent.tech2rent.R.id.nav_login -> {

                    val parameters = mapOf("prompt" to "login")
                    WebAuthProvider.login(account).withScheme("demo")
                        .withAudience(String.format("https://%s/userinfo", getString(R.string.com_auth0_domain)))
                        .withParameters(parameters)
                        .start(
                            this,
                            object: AuthCallback{
                                override fun onFailure(dialog: Dialog) {

                                }

                                override fun onFailure(exception: AuthenticationException?) {
                                }

                                override fun onSuccess(credentials: Credentials) {
                                    val usersApi = UsersAPIClient(account, credentials.accessToken)
                                    val authAPIClient = AuthenticationAPIClient(account)
                                    val token = credentials.accessToken.orEmpty()
                                    authAPIClient.userInfo(token)
                                        .start(object : BaseCallback<UserProfile, AuthenticationException> {
                                            override fun onSuccess(userinfo: UserProfile) {
                                                userinfo.id
                                                val uuid = userinfo.extraInfo.get("sub")
                                            }

                                            override fun onFailure(error: AuthenticationException) {
                                                // Show error
                                            }
                                        })
                                }
                            })


                }
            }
            drawerLayout.closeDrawers()
            true
        }
    }
}
