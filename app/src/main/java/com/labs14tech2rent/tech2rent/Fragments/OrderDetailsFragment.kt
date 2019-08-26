package com.labs14tech2rent.tech2rent.Fragments

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

import com.labs14tech2rent.tech2rent.R
import com.labs14tech2rent.tech2rent.models.Listing
import com.labs14tech2rent.tech2rent.models.User
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.fragment_order_details.*
import kotlinx.android.synthetic.main.fragment_view_profile.*
import kotlinx.android.synthetic.main.fragment_view_profile.image_profile_view
import kotlinx.android.synthetic.main.fragment_view_profile.text_name_view
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject


class OrderDetailsFragment : DialogFragment() {

    private var listing: Listing? = null
    private var user: User? = null
    val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            listing = it.getParcelable("listing") as Listing
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val profileImage: CircleImageView? = image_profile_view


        val userid = listing?.user_id
        val request: Thread = Thread(Runnable {
            /*
            * GET USER REQUEST
            * */
            val request: Request = Request.Builder().get()
                .url("http://labstech2rentstaging.herokuapp.com/api/users/$userid/reviews")
                .addHeader("Content-Type", "application/json;charset=UTF-8")
                .build()

            val response: Response = client.newCall(request).execute()

            val JSONstring = response.body()?.string()

            val profileJSON = JSONObject(JSONstring)
            try {
                user = User(
                    "unknown",
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
                    profileJSON.getDouble("average_rating"),
                    "",
                    profileJSON.getString("title")
                )
            }catch (e: Exception){
                user = User("unkown",
                    profileJSON.getString("email"),
                    profileJSON.getString("name"))
                try {
                    user?.profile_picture = profileJSON.getString("profile_picture")
                }catch (e: Exception){}
                try {
                    user?.phone = profileJSON.getString("phone")
                }catch (e: Exception){}
            }


        })
        request.start()
        request.join()

        user.let {

            if (listing?.profileImage == null) {
                try {

                    val temp: Bitmap = Picasso.get().load(user?.profile_picture).get()
                    profileImage?.setImageBitmap(temp)
                    listing?.profileImage = temp

                } catch (e: Exception) {
                }
            }else {
                profileImage?.setImageBitmap(listing?.profileImage)
            }
            text_name_view.text = user?.name
            text_phone.text = user?.phone
            text_email.text = user?.email

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_order_details, container, false)
    }


}
