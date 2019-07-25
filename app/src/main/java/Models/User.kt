package Models

data class User(val auth0_user_id: String,
                val email: String,
                val name: String,
                var profile_picture: String = "",
                var phone: String = "",
                var date_of_birth: String = "",
                var preferred_payment_type: String = "",
                var street: String = "",
                var city: String = "",
                var state: String = "",
                var zip_code: Int = 0,
                var average_rating: Double = 0.0){

    override fun toString(): String {
        return "User(auth0_user_id='$auth0_user_id'," +
                " email='$email'," +
                " name='$name'," +
                " profile_picture='$profile_picture'," +
                " phone='$phone'," +
                " date_of_birth='$date_of_birth'," +
                " preferred_payment_type='$preferred_payment_type'," +
                " street='$street'," +
                " city='$city'," +
                " state='$state'," +
                " zip_code=$zip_code," +
                " average_rating=$average_rating)"
    }
}

