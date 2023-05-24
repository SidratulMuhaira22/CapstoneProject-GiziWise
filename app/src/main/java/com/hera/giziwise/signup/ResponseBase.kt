package com.hera.giziwise.signup

import com.google.gson.annotations.SerializedName

data class ResponseBase(
    @SerializedName("error")
    val error: Boolean?,
    @SerializedName("message")
    val message: String?,
)