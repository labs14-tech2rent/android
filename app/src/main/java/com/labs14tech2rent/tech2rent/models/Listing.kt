package com.labs14tech2rent.tech2rent.models

data class Listing(val auth0_user_id: String,
                   val name: String,
                   val description: String,
                   var listing_price: String = "",
                   var location: String = "",
                   var category: String = "",
                   var preferred_payment_type: String = ""
                   ){

    override fun toString(): String {
        return "{\"users_ownerId\":\"$auth0_user_id\"," +
                "\"name\":\"$name\"," +
                "\"description\":\"$description\"," +
                "\"price\":\"$listing_price\"," +
                "\"city\":\"$location\"," +
                "\"category\":\"$category\"," +
                "\"payment_type\":\"$preferred_payment_type\"}"
    }
}

/*
{
    "id": 3,
    "users_ownerId": 1,
    "name": "APEMAN Trail Camera 16MP 1080P Wildlife Camera, Night Detection Game Camera with No Glow 940nm IR LEDs, Time Lapse, Timer, IP66 Waterproof Design",
    "price": "20.00",
    "picture": "https://ae01.alicdn.com/kf/HTB1Brc7QFXXXXbFXVXXq6xXFXXXX.jpg",
    "location": "Philadelphia, PA",
    "category": "Cameras",
    "description": "Trail cameras are fit for hunters and wildlife enthusiasts alike. These motion-activated cameras capture images day or night, whenever an animal passes into the field of view.",
    "available": true,
    "payment_type": "Online",
    "avarage_raiting": "4.50"
}*/
