package com.labs14tech2rent.tech2rent.Fragments

import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView

import com.labs14tech2rent.tech2rent.R
import com.labs14tech2rent.tech2rent.models.Listing
import com.labs14tech2rent.tech2rent.models.User
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.fragment_view_profile.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject


class ViewProfileFragment : Fragment() {

    val profileImage: CircleImageView = image_profile_view
    val textName: TextView = text_name_view
    val textTitle: TextView = text_title_view
    val textLocation: TextView = text_location_view
    val buttonTechListed: ImageButton = button_view_tech_listed
    val buttonMessage: ImageButton = button_message
    val textBio: TextView = text_bio

    private var listing: Listing? = null
    private var user: User? = null
    val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            listing = it.getSerializable("listing") as Listing
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val userid = listing?.user_id
        Thread(Runnable {
            val request: Request = Request.Builder().get()
                .url("http://labstech2rentstaging.herokuapp.com/api/users/$userid/reviews")
                .addHeader("Content-Type", "application/json;charset=UTF-8")
                .build()

            val response: Response = client.newCall(request).execute()

            val JSONstring = response.body()?.string()

            val profileJSON = JSONObject(JSONstring)
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
                profileJSON.getDouble("average_rating")
            )
            }).start()


        return inflater.inflate(R.layout.fragment_view_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*
        * POPULATE UI WITH USER DATA
        * */
        user.let {

        if (listing?.profileImage == null) {
            try {

                val temp: Bitmap = Picasso.get().load(user?.profile_picture).get()
                profileImage.setImageBitmap(temp)
                listing?.profileImage = temp

            } catch (e: Exception) {
            }
        }else {
            profileImage.setImageBitmap(listing?.profileImage)
        }
        textName.text = user?.name
        textTitle.text
        textLocation.text
        textBio.text

        }

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ViewProfileFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}
