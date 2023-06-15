package com.hera.giziwise.home.recipe

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.forEach
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hera.giziwise.R
import com.hera.giziwise.api.ApiConfig
import com.hera.giziwise.home.HomeActivity
import com.hera.giziwise.home.account.AccountActivity
import com.hera.giziwise.home.artikel.ArticleActivity
import com.hera.giziwise.home.artikel.ArticleAdapter
import com.hera.giziwise.home.artikel.DetailArticleActivity
import com.hera.giziwise.home.camera.CameraActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class RecipeActivity : AppCompatActivity() {
    private lateinit var recipeRecyclerView: RecyclerView
    private lateinit var recipeAdapter: RecipeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)

        recipeRecyclerView = findViewById(R.id.grid_recycler_view)

        recipeAdapter = RecipeAdapter(emptyList(), object : RecipeAdapter.RecipeClickListener {
            override fun onRecipeClick(recipe: Recipe) {
                // Handle recipe item click event here
            }
        })

        recipeRecyclerView.layoutManager = GridLayoutManager(this, 2)
        recipeRecyclerView.adapter = recipeAdapter

        fetchRecipes()

        // NavigationBar
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavigationView.selectedItemId = R.id.navigation_recipes
        bottomNavigationView.setOnItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    startActivity(Intent(applicationContext, HomeActivity::class.java))
                    overridePendingTransition(R.anim.slide_right, R.anim.slide_left)
                    finish()
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_camera -> {
                    startActivity(Intent(applicationContext, CameraActivity::class.java))
                    overridePendingTransition(R.anim.slide_right, R.anim.slide_left)
                    finish()
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_articles-> {
                    startActivity(Intent(applicationContext, ArticleActivity::class.java))
                    overridePendingTransition(R.anim.slide_right, R.anim.slide_left)
                    finish()
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_recipes-> return@setOnItemSelectedListener true
                R.id.navigation_account -> {
                    startActivity(Intent(applicationContext, AccountActivity::class.java))
                    overridePendingTransition(R.anim.slide_right, R.anim.slide_left)
                    finish()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

    private fun fetchRecipes() {
        val apiService = ApiConfig.getApiClient()

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = apiService.getAllRecipes("title", "desc", 1, 10, null, null)
                if (response.isSuccessful) {
                    val recipeResponse = response.body()
                    val recipes = recipeResponse?.data?.recipes ?: emptyList()

                    // Set isRecipeActivity flag to true for RecipeAdapter
                    recipeAdapter.setRecipeActivity(true)
                    recipeAdapter.updateData(recipes)
                } else {
                    // Handle API response error here
                    Toast.makeText(this@RecipeActivity, "Failed to fetch recipes", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                // Handle API call failure here
                Toast.makeText(this@RecipeActivity, "Failed to fetch recipes", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun openHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun openCameraActivity() {
        val intent = Intent(this, CameraActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun openArticleActivity() {
        val intent = Intent(this, ArticleActivity::class.java)
        startActivity(intent)
    }

    private fun openAccountActivity() {
        val intent = Intent(this, AccountActivity::class.java)
        startActivity(intent)
        finish()
    }
}


