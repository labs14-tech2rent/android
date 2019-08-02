package com.labs14tech2rent.tech2rent.models

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

) : Serializable {

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


