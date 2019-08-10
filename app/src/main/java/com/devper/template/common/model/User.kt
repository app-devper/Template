package com.devper.template.common.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "users")
data class User(

    @SerializedName("_id")
    @PrimaryKey
    var id: String,
    @SerializedName("username")
    var username: String? = null,
    @SerializedName("email")
    var email: String? = null,
    @SerializedName("firstName")
    var firstName: String? = null,
    @SerializedName("lastName")
    var lastName: String? = null,
    @SerializedName("imageUrl")
    var imageUrl: String? = null
) {

    val fullName: String
        get() = "$firstName $lastName"
}