package com.hera.giziwise.home.recipe

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.hera.giziwise.R

class InstruksiRecipeActivity : AppCompatActivity() {

    private lateinit var recipeTitle: TextView
    private lateinit var recipeInstructionWebView: WebView
    private lateinit var recipeImage: ImageView
    private lateinit var buttonInstruksi: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_resep_instruksi)

        recipeTitle = findViewById(R.id.resep_title)
        recipeInstructionWebView = findViewById(R.id.instruksi)
        recipeImage = findViewById(R.id.preview_image)
        buttonInstruksi = findViewById(R.id.button_instruksi)
        buttonInstruksi.isEnabled = false

        val recipeTitle = intent.getStringExtra("RECIPE_TITLE")
        val recipeInstruction = intent.getStringExtra("RECIPE_INSTRUCTION")
        val recipeIngredient = intent.getStringExtra("RECIPE_INGREDIENTS")
        val recipeImageUrl = intent.getStringExtra("RECIPE_IMAGE_URL")

        // Tampilkan data pada tampilan
        recipeTitle?.let { setTitle(it) }
        recipeInstruction?.let { setInstruction(it) }
        recipeImageUrl?.let { setRecipeImage(it) }
    }

    private fun setTitle(title: String) {
        recipeTitle.text = title
    }

    private fun setInstruction(instruction: String) {
        recipeInstructionWebView.loadDataWithBaseURL(null, instruction, "text/html", "UTF-8", null)
    }

    private fun setRecipeImage(imageUrl: String) {
        Glide.with(this)
            .load(imageUrl)
            .into(recipeImage)
    }

    fun buttonBahan(view: View) {
        onBackPressed()
    }

    fun goBack(view: View) {
        finish()
    }
}

