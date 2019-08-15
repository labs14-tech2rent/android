package com.labs14tech2rent.tech2rent.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.labs14tech2rent.tech2rent.R
import com.labs14tech2rent.tech2rent.models.Listing


class DetailsDailyConditionDescPagerAdapter(val listing: Listing) : PagerAdapter() {

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == obj
    }

    override fun getCount(): Int {
        return 3
    }

    override fun instantiateItem(container: ViewGroup, positionInput: Int): View {
        val inflater: LayoutInflater =
            container.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        var position = positionInput

        if(position > 2){
            position = 2
        }

        var variableGroup = 0
        var view: View? = null
        when (position) {
            0 -> {
                view = inflater.inflate(R.layout.details_daily_container, container, false)
                variableGroup = 1
            }

            1 -> {
                view = inflater.inflate(R.layout.details_condition_container, container, false)
                variableGroup = 2
            }

            2 -> {
                view = inflater.inflate(R.layout.details_description_container, container, false)
                variableGroup = 3
            }
        }

        container.addView(view)
        populateUI(variableGroup, container)
        return view!!
    }

    fun populateUI(variableGroup: Int, view: ViewGroup) {
        when (variableGroup) {
            1 -> {
                val textRate = view.findViewById<TextView>(R.id.text_daily_rate)
                textRate.text = listing.listing_price
            }
            2 -> {
                val textCondition = view.findViewById<TextView>(R.id.text_condition)
                textCondition.text = listing.condition
            }
            3 -> {
                val textDescription = view.findViewById<TextView>(R.id.text_description)
                textDescription.text = listing.description
            }
        }
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View)
    }
}