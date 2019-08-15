package com.labs14tech2rent.tech2rent.activities

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.labs14tech2rent.tech2rent.R
import com.labs14tech2rent.tech2rent.adapters.DetailsDailyConditionDescPagerAdapter
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
        val textDailyRate: TextView = text_daily_rate
        val textCondition: TextView = text_condition
        val textDescription: TextView = text_description
        val viewPagerDailyConditionDesc: ViewPager = view_pager_daily_condition_desc

        val dailyConditionDescAdapter = DetailsDailyConditionDescPagerAdapter()
        viewPagerDailyConditionDesc.adapter = dailyConditionDescAdapter

        textDailyRate.setOnClickListener(View.OnClickListener {
            viewPagerDailyConditionDesc.currentItem = 0
            changeHighlighted(1)
        })
        textCondition.setOnClickListener(View.OnClickListener {
            viewPagerDailyConditionDesc.currentItem = 1
            changeHighlighted(2)
        })
        textDescription.setOnClickListener(View.OnClickListener {
            viewPagerDailyConditionDesc.currentItem = 2
            changeHighlighted(3)
        })

        viewPagerDailyConditionDesc.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
            override fun onPageSelected(position: Int) {
                when(position){
                    0 -> {
                        changeHighlighted(1)
                    }
                    1 -> {
                        changeHighlighted(2)
                    }
                    2 -> {
                        changeHighlighted(3)
                    }
                }
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageScrollStateChanged(state: Int) {
            }

        })



        textTitle.text = listing.name


    }


    fun changeHighlighted(position: Int){
        highlight_daily_rate.visibility = View.GONE
        highlight_condition.visibility = View.GONE
        highlight_description.visibility = View.GONE

        when(position){
            1 -> {highlight_daily_rate.visibility = View.VISIBLE}
            2 -> {highlight_condition.visibility = View.VISIBLE}
            3 -> {highlight_description.visibility = View.VISIBLE}
        }
    }
}

