package com.hera.giziwise.home.artikel

data class ArticleResponse(
    val statusCode: Int,
    val messages: List<String>,
    val data: ArticleData
)

data class ArticleData(
    val articles: List<Article>,
    val page: Int,
    val limit: Int,
    val totalData: Int,
    val totalPage: Int
)

data class Article(
    val id: Int,
    val title: String,
    val summary: String,
    val image: String?,
    val isFeatured: Boolean,
    val authorId: String,
    val createdAt: String,
    val updatedAt: String,
    val articleTags: List<ArticleTag>,
    val author: Author
)

data class ArticleTag(
    val tagId: Int,
    val tag: Tag
)

data class Tag(
    val name: String
)

data class Author(
    val name: String
)
