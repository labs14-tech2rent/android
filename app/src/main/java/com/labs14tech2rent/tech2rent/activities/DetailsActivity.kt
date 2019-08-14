package com.labs14tech2rent.tech2rent.activities

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.labs14tech2rent.tech2rent.R
import com.labs14tech2rent.tech2rent.models.Listing
import kotlinx.android.synthetic.main.activity_details.*
import org.json.JSONObject


class DetailsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val listingString = intent.getStringExtra("listing")
        val listingJSON = JSONObject(listingString)
        val listing = Listing(listingJSON)

        val textTitle: TextView = text_title
        val dotLeft: ImageView = dot_left
        val dotCenter: ImageView = dot_center
        val dotRight: ImageView = dot_right
        val highlightDailyRate: ImageView = highlight_daily_rate
        val highlightCondition: ImageView = highlight_condition
        val highlightDescription: ImageView = highlight_description
        val viewPagerDailyConditionDesc: ViewPager = view_pager_daily_condition_desc

        textTitle.text = listing.name


    }
}
