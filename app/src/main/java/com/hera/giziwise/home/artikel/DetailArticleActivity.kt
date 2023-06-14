package com.hera.giziwise.home.artikel

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.hera.giziwise.R

class DetailArticleActivity : AppCompatActivity() {
    private lateinit var previewImage: ImageView
    private lateinit var titleArticle: TextView
    private lateinit var published: TextView
    private lateinit var contentWebView: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_article)

        previewImage = findViewById(R.id.preview_image)
        titleArticle = findViewById(R.id.title_article)
        published = findViewById(R.id.published)
        contentWebView = findViewById(R.id.content_webview)

        val imageUrl = intent.getStringExtra("ARTICLE_IMAGE_URL")
        val articleTitle = intent.getStringExtra("ARTICLE_TITLE")
        val articlePublished = intent.getStringExtra("ARTICLE_CREATED_AT")
        val articleContent = intent.getStringExtra("ARTICLE_CONTENT")

        Glide.with(this)
            .load(imageUrl)
            .into(previewImage)

        titleArticle.text = articleTitle
        published.text = articlePublished

        contentWebView.settings.javaScriptEnabled = true

        contentWebView.settings.javaScriptEnabled = true
        if (articleContent != null) {
            contentWebView.loadDataWithBaseURL(null, articleContent, "text/html", "UTF-8", null)
        }
    }

    fun goBack(view: View) {
        finish()
    }
}







