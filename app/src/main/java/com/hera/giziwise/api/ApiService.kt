package com.hera.giziwise.api

import com.hera.giziwise.login.LoginRequest
import com.hera.giziwise.login.LoginResponse
import com.hera.giziwise.signup.ResponseBase
import com.hera.giziwise.signup.SignUpRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("signup")
    fun signup(
        @Body request: SignUpRequest
    ): Call<ResponseBase>

    @POST("login")
    fun login(
        @Body request: LoginRequest
    ): Call<LoginResponse>
}