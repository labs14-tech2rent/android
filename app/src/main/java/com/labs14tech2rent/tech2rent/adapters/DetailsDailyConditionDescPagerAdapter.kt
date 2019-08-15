package com.labs14tech2rent.tech2rent.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.labs14tech2rent.tech2rent.R

class DetailsDailyConditionDescPagerAdapter() : PagerAdapter() {
    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == obj
    }

    override fun getCount(): Int {
        return 3
    }

    fun instantiateView(container: ViewGroup, position: Int) {
        val inflater: LayoutInflater =
            container.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        var resId = 1

        var variableGroup = 0
        when (position) {
            1 -> {
                resId = R.layout.details_daily_container
                variableGroup = 1
            }

            2 -> {
                resId = R.layout.details_condition_container
                variableGroup = 2
            }

            3 -> {
                resId = R.layout.details_description_container
                variableGroup = 3
            }
        }

        val view: View = inflater.inflate(resId, container, false)
        container.addView(view)
        populateUI(variableGroup)
    }

    fun populateUI(variableGroup: Int){
        when(variableGroup){
            1 -> {

            }
            2 -> {

            }
            3 -> {

            }
        }
    }
}