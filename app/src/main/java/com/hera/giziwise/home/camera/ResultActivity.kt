package com.hera.giziwise.home.camera

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.hera.giziwise.R

class ResultActivity : AppCompatActivity() {
    private lateinit var productNameTextView: TextView
    private lateinit var productTkpisTextView: TextView
    private lateinit var productImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        productNameTextView = findViewById(R.id.result)
        productTkpisTextView = findViewById(R.id.nutrition_result)
        productImageView = findViewById(R.id.preview_image)

        val productName = intent.getStringExtra("productName")
        val productImage = intent.getStringExtra("productImage")
        val productTkpis = intent.getStringExtra("productTkpis")

        productNameTextView.text = productName
        productTkpisTextView.text = productTkpis
        Glide.with(this).load(productImage).into(productImageView)
    }

    fun goBack(view: View) {
        finish()
    }
}





