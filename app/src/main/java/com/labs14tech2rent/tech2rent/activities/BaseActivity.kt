package com.labs14tech2rent.tech2rent.activities

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
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
import okhttp3.*
import org.json.JSONObject


abstract class BaseActivity : AppCompatActivity() {

    override fun onStart() {
        super.onStart()
        val context = this
        val toolbar: Toolbar = tool_bar as Toolbar
        toolbar.setTitleTextColor(resources.getColor(R.color.ux_color_1_off_white))
        toolbar.title = ""
        setSupportActionBar(toolbar)

        val drawerLayout = drawer_layout
        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val navView: NavigationView = nav_view
        nav_view.setBackgroundColor(resources.getColor(R.color.ux_color_6_dark_gray))
        nav_view.itemTextColor = resources.getColorStateList(R.color.nav_view_color_state_list)
        nav_view.setCheckedItem(R.id.nav_login)
        nav_view.menu.getItem(4).isChecked = true

        val account: Auth0 = Auth0(context)
        //account.isOIDCConformant = true
        val client: OkHttpClient = OkHttpClient()

        navView.setNavigationItemSelectedListener {it ->
            when(it.itemId){
                R.id.nav_login -> {

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
                                                var uuid: String = userinfo.extraInfo.get("sub").toString()
                                                val sharedprefs: SharedPreferences = getSharedPreferences("acct", Context.MODE_PRIVATE)
                                                val editor = sharedprefs.edit()
                                                editor.putString("uuid", uuid)
                                                editor.apply()


                                                val body = FormBody.Builder().add("auth0_user_id", uuid).build()

                                                val request: Request = Request.Builder().url("http://labstech2rentstaging.herokuapp.com/api/users/findUser")
                                                    .post(body)
                                                    .build()
                                                val response: Response = client.newCall(request).execute()

                                                val JSONstring = response.body?.string()

                                                val JSON = JSONObject(JSONstring)
                                                try {
                                                    if (JSON.getString("message").equals("User not found")) {
                                                        /*
                                                    * REDIRECT TO FINISH PROFILE
                                                    * */
                                                    }
                                                }catch (e: Exception){ }


                                                println(JSONstring)

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
