package com.hera.giziwise.home.recipe

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.hera.giziwise.R
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class BahanRecipeActivity : AppCompatActivity() {

    private lateinit var recipeImage: ImageView
    private lateinit var recipeTitle: TextView
    private lateinit var ingredientsWebView: WebView
    private lateinit var recipeInstruction: String
    private lateinit var recipeImageUrl: String
    private lateinit var buttonBahan: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_resep_bahan)

        recipeImage = findViewById(R.id.preview_image)
        recipeTitle = findViewById(R.id.resep_title)
        ingredientsWebView = findViewById(R.id.bahan_bahan)
        buttonBahan = findViewById(R.id.button_bahan)
        buttonBahan.isEnabled = false

        val recipeTitle = intent.getStringExtra("RECIPE_TITLE")
        val recipeIngredients = intent.getStringExtra("RECIPE_INGREDIENTS")
        recipeInstruction = intent.getStringExtra("RECIPE_INSTRUCTIONS").toString()
        recipeImageUrl = intent.getStringExtra("RECIPE_IMAGE_URL").toString()

        // Tampilkan data pada tampilan
        recipeTitle?.let { setTitle(it) }
        recipeIngredients?.let { setIngredients(it) }
        recipeImageUrl?.let { setRecipeImage(it) }
    }

    private fun setTitle(title: String) {
        recipeTitle.text = title
    }

    private fun setIngredients(ingredients: String) {
        ingredientsWebView.loadDataWithBaseURL(null, ingredients, "text/html", "UTF-8", null)
    }

    private fun setRecipeImage(imageUrl: String) {
        Glide.with(this)
            .load(imageUrl)
            .into(recipeImage)
    }

    fun buttonInstruksi(view: View) {
        // Tambahkan intent untuk membuka halaman instruksi
        val intent = Intent(this, InstruksiRecipeActivity::class.java)
        intent.putExtra("RECIPE_TITLE", recipeTitle.text.toString())
        intent.putExtra("RECIPE_INSTRUCTION", recipeInstruction)
        intent.putExtra("RECIPE_IMAGE_URL", recipeImageUrl)
        startActivity(intent)
    }

    fun goBack(view: View) {
        finish()
    }
}

