package com.hera.giziwise.home.recipe

data class RecipeResponse(
    val statusCode: Int,
    val messages: List<String>,
    val data: RecipeData
)

data class RecipeData(
    val recipes: List<Recipe>,
    val page: Int,
    val limit: Int,
    val totalData: Int,
    val totalPage: Int
)

data class Recipe(
    val id: Int,
    val title: String,
    val summary: String,
    val image: String,
    val isFeatured: Boolean,
    val authorId: String,
    val createdAt: String,
    val updatedAt: String,
    val recipeTags: List<RecipeTag>,
    val author: Author,
    val ingredients: String,
    val instructions: String
)

data class RecipeTag(
    val tagId: Int,
    val tag: Tag
)

data class Tag(
    val name: String
)

data class Author(
    val name: String
)
