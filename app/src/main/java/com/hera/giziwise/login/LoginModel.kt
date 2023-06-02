package com.hera.giziwise.login

import com.google.gson.annotations.SerializedName

data class LoginModel(
    @SerializedName("userId")
    val userId: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("token")
    val token: String?,
)