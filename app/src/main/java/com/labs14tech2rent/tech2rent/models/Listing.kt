package com.labs14tech2rent.tech2rent.models

import android.graphics.Bitmap
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter
import org.json.JSONObject
import java.io.Serializable

data class Listing(
    val item_id: Int,
    val user_id: Int,
    val name: String,
    val listing_price: String,
    val picture_url: String,
    val category: String,
    val description: String,
    val available: Boolean,
    val preferred_payment_type: String,
    val average_rating: String,
    val condition: String,
    val sub_category: String,
    val city: String,
    val state: String,
    val zip_code: String



) : SortedListAdapter.ViewModel, Serializable  {
    override fun <T : Any?> isContentTheSameAs(model: T): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun <T : Any?> isSameModelAs(model: T): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    var profileImage: Bitmap? = null
    var displayImage: Bitmap? = null

    constructor(json: JSONObject) : this(
        json.getInt("id"),
        json.getInt("users_ownerId"),
        json.getString("name"),
        json.getString("price"),
        json.getString("picture"),
        json.getString("category"),
        json.getString("description"),
        json.getBoolean("available"),
        json.getString("payment_type"),
        json.getString("average_rating"),
        json.getString("condition"),
        json.getString("sub_category"),
        json.getString("city"),
        json.getString("state"),
        json.getString("zipcode"
            )
        )

    override fun toString(): String {
        return "{\"users_ownerId\":\"$user_id\"," +
                "\"name\":\"$name\"," +
                "\"price\":\"$listing_price\"," +
                "\"picture\":\"$picture_url\"," +
                "\"category\":\"$category\"," +
                "\"description\":\"$description\"," +
                "\"available\":\"$available\"," +
                "\"payment_type\":\"$preferred_payment_type\"," +
                "\"condition\":\"$condition\"," +
                "\"sub_category\":\"$sub_category\"," +
                "\"price\":\"$listing_price\"," +
                "\"city\":\"$city\"," +
                "\"state\":\"$state\"," +
                "\"zipcode\":\"$zip_code\"}"
    }


}


