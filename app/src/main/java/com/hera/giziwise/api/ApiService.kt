package com.hera.giziwise.api

import com.hera.giziwise.home.artikel.ArticleDetailResponse
import com.hera.giziwise.home.artikel.ArticleResponse
import com.hera.giziwise.home.camera.ProductResponse
import com.hera.giziwise.home.recipe.RecipeResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @Multipart
    @POST("products/search-by-image")
    suspend fun searchProductByImage(
        @Part image: MultipartBody.Part
    ): Response<ProductResponse>

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
    suspend fun getArticleById(@Path("id") id: Int): Response<ArticleDetailResponse>

    @GET("recipes")
    suspend fun getAllRecipes(
        @Query("sort") sort: String,
        @Query("order") order: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("title") title: String?,
        @Query("isFeatured") isFeatured: Boolean?
    ): Response<RecipeResponse>

}



