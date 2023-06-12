package com.hera.giziwise.home

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.hera.giziwise.R
import com.hera.giziwise.home.camera.CameraActivity
import com.hera.giziwise.home.artikel.ArticleActivity
import com.hera.giziwise.home.recipe.RecipeActivity
import com.hera.giziwise.home.account.AccountActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.forEach
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hera.giziwise.api.ApiConfig
import com.hera.giziwise.home.artikel.ArticleAdapter
import com.hera.giziwise.home.recipe.Recipe
import com.hera.giziwise.home.recipe.RecipeAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class HomeActivity : AppCompatActivity() {

    private lateinit var bottomNavView: BottomNavigationView
    private lateinit var searchHomeEditText: EditText
    private lateinit var articleRecyclerView: RecyclerView
    private lateinit var recipeRecyclerView: RecyclerView
    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var recipeAdapter: RecipeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        bottomNavView = findViewById(R.id.nav_view)
        searchHomeEditText = findViewById(R.id.search_home)
        articleRecyclerView = findViewById(R.id.article_kesehatan_recycler_view)
        recipeRecyclerView = findViewById(R.id.resep_sehat_recycler_view)

        bottomNavView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    openHomeActivity()
                    true
                }
                R.id.navigation_camera -> {
                    openCameraActivity()
                    true
                }
                R.id.navigation_articles -> {
                    openArticleActivity()
                    true
                }
                R.id.navigation_recipes -> {
                    openRecipesActivity()
                    true
                }
                R.id.navigation_account -> {
                    openAccountActivity()
                    true
                }
                else -> false
            }
        }

        setIconColors(R.id.navigation_home)

        bottomNavView.setOnNavigationItemReselectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {

                }
                R.id.navigation_camera -> {

                }
                R.id.navigation_articles -> {

                }
                R.id.navigation_recipes -> {

                }
                R.id.navigation_account -> {

                }
            }
        }

        searchHomeEditText.setOnClickListener {
            openCameraActivity()
        }

        // Inisialisasi RecyclerView dan Adapter untuk artikel
        articleAdapter = ArticleAdapter()
        articleRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        articleRecyclerView.adapter = articleAdapter

        // Inisialisasi RecyclerView dan Adapter untuk resep
        val recipeList = listOf<Recipe>() // Initialize an empty list or provide the actual recipe list
        recipeAdapter = RecipeAdapter(recipeList)
        recipeRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recipeRecyclerView.adapter = recipeAdapter

        // Mengambil data artikel dan resep dari API
        fetchArticles()
        fetchRecipes()
    }

    private fun fetchArticles() {
        val apiService = ApiConfig.getApiClient()

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = apiService.getAllArticles("title", "desc", 1, 10, null, null)
                if (response.isSuccessful) {
                    val articleResponse = response.body()
                    val articles = articleResponse?.data?.articles ?: emptyList()
                    articleAdapter.updateData(articles)
                } else {
                    // Tangani kesalahan saat respons API tidak berhasil
                    Toast.makeText(this@HomeActivity, "Gagal memuat artikel", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                // Tangani kesalahan saat panggilan API gagal
                Toast.makeText(this@HomeActivity, "Gagal memuat artikel", Toast.LENGTH_SHORT).show()
            }
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
                    recipeAdapter.updateData(recipes)
                } else {
                    // Tangani kesalahan saat respons API tidak berhasil
                    Toast.makeText(this@HomeActivity, "Gagal memuat resep", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                // Tangani kesalahan saat panggilan API gagal
                Toast.makeText(this@HomeActivity, "Gagal memuat resep", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun openHomeActivity() {
        setIconColors(R.id.navigation_home)
    }

    private fun openCameraActivity() {
        val intent = Intent(this, CameraActivity::class.java)
        startActivity(intent)
    }

    private fun openArticleActivity() {
        val intent = Intent(this, ArticleActivity::class.java)
        startActivity(intent)
    }

    private fun openRecipesActivity() {
        val intent = Intent(this, RecipeActivity::class.java)
        startActivity(intent)
    }

    private fun openAccountActivity() {
        val intent = Intent(this, AccountActivity::class.java)
        startActivity(intent)
    }

    private fun setIconColors(selectedItemId: Int) {
        val menu = bottomNavView.menu
        menu.forEach { item ->
            val icon = item.icon
            if (item.itemId == selectedItemId) {
                icon?.setTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.green)))
            } else {
                icon?.setTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.grey)))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        setIconColors(R.id.navigation_home)
    }
}

