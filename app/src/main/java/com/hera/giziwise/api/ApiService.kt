package com.hera.giziwise.api

import com.hera.giziwise.home.artikel.ArticleResponse
import com.hera.giziwise.home.camera.ProductResponse
import com.hera.giziwise.home.recipe.RecipeResponse
import com.hera.giziwise.login.LoginRequest
import com.hera.giziwise.login.LoginResponse
import com.hera.giziwise.signup.ResponseBase
import com.hera.giziwise.signup.SignUpRequest
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    // Endpoint untuk autentikasi
    @POST("signup")
    fun signup(
        @Body request: SignUpRequest
    ): Call<ResponseBase>

    @POST("login")
    fun login(
        @Body request: LoginRequest
    ): Call<LoginResponse>

    // End point untuk produk
    @GET("products")
    suspend fun getAllProducts(
        @Query("sort") sort: String,
        @Query("order") order: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("name") name: String?,
        @Query("code") code: String?,
        @Query("categoryId") categoryId: Int?,
        @Query("type") type: String?
    ): Response<ProductResponse>

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: String): Response<ProductResponse>

    @Multipart
    @POST("products/search-by-image")
    suspend fun searchProductByImage(
        @Part image: MultipartBody.Part
    ): Response<ProductResponse>

    // Endpoint untuk artikel
    @GET("articles")
    suspend fun getAllArticles(
        @Query("sort") sort: String,
        @Query("order") order: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("title") title: String?,
        @Query("isFeatured") isFeatured: Boolean?
    ): Response<ArticleResponse>

    @GET("articles/{id}")
    suspend fun getArticleById(@Path("id") id: String): Response<ArticleResponse>

    // Endpoint untuk resep
    @GET("recipes")
    suspend fun getAllRecipes(
        @Query("sort") sort: String,
        @Query("order") order: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("title") title: String?,
        @Query("isFeatured") isFeatured: Boolean?
    ): Response<RecipeResponse>

    @GET("recipes/{id}")
    suspend fun getRecipeById(@Path("id") id: String): Response<RecipeResponse>
}



