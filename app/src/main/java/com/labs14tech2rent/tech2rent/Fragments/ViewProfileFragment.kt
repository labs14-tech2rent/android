package com.labs14tech2rent.tech2rent.Fragments

import android.content.Context
import android.net.Uri
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            listing = it.getSerializable("listing") as Listing
        }
    }

    override fun onCreateView(

        /*
        *
        * GET USER FROM LISTING
        *
        * */

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_view_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*
        * POPULATE UI WITH USER DATA
        * */

        user.let {
            //Picasso.get().load("").into(profileImage)

            textName.text = user!!.name

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
