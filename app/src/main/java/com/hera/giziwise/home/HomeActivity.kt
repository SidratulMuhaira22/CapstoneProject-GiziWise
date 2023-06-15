package com.hera.giziwise.home

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.MenuItem
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
import com.hera.giziwise.home.artikel.Article
import com.hera.giziwise.home.artikel.ArticleAdapter
import com.hera.giziwise.home.artikel.DetailArticleActivity
import com.hera.giziwise.home.recipe.BahanRecipeActivity
import com.hera.giziwise.home.recipe.Recipe
import com.hera.giziwise.home.recipe.RecipeAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class HomeActivity : AppCompatActivity() {

    private lateinit var searchHomeEditText: EditText
    private lateinit var articleRecyclerView: RecyclerView
    private lateinit var recipeRecyclerView: RecyclerView
    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var recipeAdapter: RecipeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        searchHomeEditText = findViewById(R.id.search_home)
        articleRecyclerView = findViewById(R.id.article_kesehatan_recycler_view)
        recipeRecyclerView = findViewById(R.id.resep_sehat_recycler_view)


        searchHomeEditText.setOnClickListener {
            openCameraActivity()
        }

        // Inisialisasi RecyclerView dan Adapter untuk artikel
        articleAdapter = ArticleAdapter(object : ArticleAdapter.ArticleClickListener {
            override fun onArticleClick(article: Article) {
                openDetailArticleActivity(article)
            }
        })
        articleRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        articleRecyclerView.adapter = articleAdapter

        // Inisialisasi RecyclerView dan Adapter untuk resep
        val recipeList = listOf<Recipe>() // Initialize an empty list or provide the actual recipe list

        recipeAdapter = RecipeAdapter(recipeList, object : RecipeAdapter.RecipeClickListener {
            override fun onRecipeClick(recipe: Recipe) {
                openDetailRecipeActivity(recipe)
            }
        })

        recipeRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recipeRecyclerView.adapter = recipeAdapter

        // Mengambil data artikel dan resep dari API
        fetchArticles()
        fetchRecipes()

        // NavigationBar
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavigationView.selectedItemId = R.id.navigation_home
        bottomNavigationView.setOnItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.navigation_home -> return@setOnItemSelectedListener true

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
                R.id.navigation_recipes-> {
                    startActivity(Intent(applicationContext, RecipeActivity::class.java))
                    overridePendingTransition(R.anim.slide_right, R.anim.slide_left)
                    finish()
                    return@setOnItemSelectedListener true
                }
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


    private fun openCameraActivity() {
        val intent = Intent(this, CameraActivity::class.java)
        startActivity(intent)
    }


    private fun openDetailArticleActivity(article: Article) {
        val intent = Intent(this, DetailArticleActivity::class.java)
        intent.putExtra("ARTICLE_ID", article.id)
        intent.putExtra("ARTICLE_TITLE", article.title)
        intent.putExtra("ARTICLE_CREATED_AT", article.createdAt)
        intent.putExtra("ARTICLE_CONTENT", article.content)
        intent.putExtra("ARTICLE_IMAGE_URL", article.image)
        startActivity(intent)
    }

    private fun openDetailRecipeActivity(recipe: Recipe) {
        val intent = Intent(this, BahanRecipeActivity::class.java)
        intent.putExtra("RECIPE_ID", recipe.id)
        intent.putExtra("RECIPE_TITLE", recipe.title)
        intent.putExtra("RECIPE_INGREDIENTS", recipe.ingredients)
        intent.putExtra("RECIPE_IMAGE_URL", recipe.image)
        intent.putExtra("RECIPE_INSTRUCTIONS", recipe.instructions)
        startActivity(intent)
    }


    override fun onResume() {
        super.onResume()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}



