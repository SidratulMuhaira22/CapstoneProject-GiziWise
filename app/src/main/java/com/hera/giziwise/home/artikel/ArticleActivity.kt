package com.hera.giziwise.home.artikel

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.forEach
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hera.giziwise.R
import com.hera.giziwise.api.ApiConfig
import com.hera.giziwise.home.HomeActivity
import com.hera.giziwise.home.account.AccountActivity
import com.hera.giziwise.home.camera.CameraActivity
import com.hera.giziwise.home.recipe.RecipeActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class ArticleActivity : AppCompatActivity(), ArticleAdapter.ArticleClickListener {
    private lateinit var articleRecyclerView: RecyclerView
    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var imageView2: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)

        articleRecyclerView = findViewById(R.id.recyclerView_lainnya)
        imageView2 = findViewById(R.id.imageView2)


        val textView: TextView = findViewById(R.id.textView)
        val imageUrl = "sehat-3832311830.jpg"

        Glide.with(this)
            .load(imageUrl)
            .into(imageView2)

        // Setel teks artikel
        val articleText = "Pola dan gaya hidup modern cukup berpengaruh terhadap perubahan pola makan yang sering dikonsumsi sehari-hari."
        val spannableString = SpannableString(articleText)

        // Tambahkan ClickableSpan untuk teks
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                openDetailArticleActivity(imageUrl, getString(R.string.isi_artikel))
            }
        }
        spannableString.setSpan(clickableSpan, 0, articleText.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        // Tambahkan ClickableSpan untuk gambar
        val clickableImageSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                openDetailArticleActivity(imageUrl, getString(R.string.isi_artikel))
            }
        }
        spannableString.setSpan(clickableImageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        // Setel teks dan gambar ke TextView
        textView.text = spannableString
        textView.movementMethod = LinkMovementMethod.getInstance()
        textView.highlightColor = Color.TRANSPARENT  // Menghilangkan highlight saat teks diklik
        textView.setOnClickListener {
            openDetailArticleActivity(imageUrl, getString(R.string.isi_artikel))
        }
        imageView2.setOnClickListener {
            openDetailArticleActivity(imageUrl, getString(R.string.isi_artikel))
        }

        // Inisialisasi RecyclerView dan Adapter untuk artikel
        articleAdapter = ArticleAdapter(this)
        articleRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        articleRecyclerView.adapter = articleAdapter

        fetchArticles()
        fetchArticles1()

        // NavigationBar
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavigationView.selectedItemId = R.id.navigation_articles
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
                R.id.navigation_articles-> return@setOnItemSelectedListener true
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

    override fun onArticleClick(article: Article) {
        openDetailArticleActivity(article.image, article.content)
    }

    private fun openDetailArticleActivity(imageUrl: String?, articleContent: String) {
        val intent = Intent(this, DetailArticleActivity::class.java)
        intent.putExtra("ARTICLE_IMAGE_URL", imageUrl)
        intent.putExtra("ARTICLE_CONTENT", articleContent)
        startActivity(intent)
    }

    private fun fetchArticles() {
        val apiService = ApiConfig.getApiClient()

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = apiService.getAllArticles("title", "desc", 1, 10, null, null)
                if (response.isSuccessful) {
                    val articleResponse = response.body()
                    val articles = articleResponse?.data?.articles ?: emptyList()

                    // Menghapus artikel dengan ID 2
                    val filteredArticles = articles.filter { article -> article.id != 2 }

                    // Membalikkan urutan daftar artikel
                    val reversedArticles = filteredArticles.reversed()

                    articleAdapter.updateData(reversedArticles)
                } else {
                    // Tangani kesalahan saat respons API tidak berhasil
                    Toast.makeText(this@ArticleActivity, "Gagal memuat artikel", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                // Tangani kesalahan saat panggilan API gagal
                Toast.makeText(this@ArticleActivity, "Gagal memuat artikel", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fetchArticles1() {
        val apiService = ApiConfig.getApiClient()

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = apiService.getAllArticles("title", "desc", 1, 10, null, null)
                if (response.isSuccessful) {
                    val articleResponse = response.body()
                    val articles = articleResponse?.data?.articles ?: emptyList()

                    // Cari artikel dengan ID 9
                    val articleId9 = articles.find { it.id == 2 }
                    if (articleId9 != null) {
                        // Jika artikel dengan ID 9 ditemukan, ambil URL gambarnya
                        val imageUrl = articleId9.image

                        // Muat gambar menggunakan Glide ke imageView2
                        Glide.with(this@ArticleActivity)
                            .load(imageUrl)
                            .into(imageView2)
                    }
                } else {
                    // Tangani kesalahan saat respons API tidak berhasil
                    Toast.makeText(this@ArticleActivity, "Gagal memuat artikel", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                // Tangani kesalahan saat panggilan API gagal
                Toast.makeText(this@ArticleActivity, "Gagal memuat artikel", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun goBack(view: View) {
        finish()
    }
}

