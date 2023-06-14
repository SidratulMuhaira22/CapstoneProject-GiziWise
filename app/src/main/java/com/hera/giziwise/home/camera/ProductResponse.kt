package com.hera.giziwise.home.camera

data class ProductResponse(
    val statusCode: Int,
    val messages: List<String>,
    val data: Product
)

data class ProductData(
    val products: List<Product>,
    val page: Int,
    val limit: Int,
    val totalData: Int,
    val totalPage: Int
)

data class Product(
    val id: Int,
    val code: String,
    val name: String,
    val latinName: String?,
    val origin: String?,
    val categoryId: Int,
    val type: String,
    val description: String?,
    val image: String?,
    val servingSize: Int,
    val servingUnit: String,
    val ediblePortion: Int,
    val category: Category,
    val tkpis: ArrayList<Tkpi>
)

data class Category(
    val id: Int,
    val name: String,
    val description: String?,
    val image: String?
)

data class Tkpi(
    val id: Int,
    val name: String,
    val symbol: String,
    val value: Double,
    val unit: String
)

