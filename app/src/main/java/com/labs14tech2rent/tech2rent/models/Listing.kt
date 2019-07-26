package com.labs14tech2rent.tech2rent.models

data class Listing(val auth0_user_id: String,
                   val title: String,
                   val description: String,
                   var listing_price: String = "",
                   var location: String = "",
                   var category: String = "",
                   var preferred_payment_type: String = ""
                   ){

    override fun toString(): String {
        return "User(auth0_user_id='$auth0_user_id'," +
                " title='$title'," +
                " name='$description'," +
                " listing_price='$listing_price'," +
                " phone='$location'," +
                " category='$category'," +
                " preferred_payment_type='$preferred_payment_type')"
    }
}

