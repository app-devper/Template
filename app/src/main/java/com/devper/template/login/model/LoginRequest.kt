package com.devper.template.login.model

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("username")
    val username: String,
    @SerializedName("pwd")
    val password: String,
    @SerializedName("channel")
    val channel: String = "app",
    @SerializedName("deviceId")
    val deviceId: String?,
    @SerializedName("deviceType")
    val deviceType: String?,
    @SerializedName("deviceToken")
    val deviceToken: String?
)