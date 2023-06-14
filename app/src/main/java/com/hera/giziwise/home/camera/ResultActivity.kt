package com.hera.giziwise.home.camera

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.hera.giziwise.R
import android.annotation.SuppressLint

class ResultActivity : AppCompatActivity() {
    private lateinit var productNameTextView: TextView
    private lateinit var productTkpisWebView: WebView
    private lateinit var productImageView: ImageView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        productNameTextView = findViewById(R.id.result)
        productTkpisWebView = findViewById(R.id.nutrition_result)
        productImageView = findViewById(R.id.preview_image)

        val productName = intent.getStringExtra("productName")
        val productImage = intent.getStringExtra("productImage")
        val productTkpis = intent.getStringExtra("productTkpis")

        productNameTextView.text = productName

        // Enable JavaScript for the WebView
        productTkpisWebView.settings.javaScriptEnabled = true

        // Load the HTML content into the WebView
        if (productTkpis != null) {
            productTkpisWebView.loadData(productTkpis, "text/html", "UTF-8")
        }

        Glide.with(this).load(productImage).into(productImageView)
    }

    fun goBack(view: View) {
        finish()
    }
}






