package com.labs14tech2rent.tech2rent.models

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable
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



) : Parcelable {


    var profileImage: Bitmap? = null
    var displayImage: Bitmap? = null

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
        profileImage = parcel.readParcelable(Bitmap::class.java.classLoader)
        displayImage = parcel.readParcelable(Bitmap::class.java.classLoader)
    }

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

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(item_id)
        parcel.writeInt(user_id)
        parcel.writeString(name)
        parcel.writeString(listing_price)
        parcel.writeString(picture_url)
        parcel.writeString(category)
        parcel.writeString(description)
        parcel.writeByte(if (available) 1 else 0)
        parcel.writeString(preferred_payment_type)
        parcel.writeString(average_rating)
        parcel.writeString(condition)
        parcel.writeString(sub_category)
        parcel.writeString(city)
        parcel.writeString(state)
        parcel.writeString(zip_code)
        parcel.writeParcelable(profileImage, flags)
        parcel.writeParcelable(displayImage, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Listing> {
        override fun createFromParcel(parcel: Parcel): Listing {
            return Listing(parcel)
        }

        override fun newArray(size: Int): Array<Listing?> {
            return arrayOfNulls(size)
        }
    }


}


