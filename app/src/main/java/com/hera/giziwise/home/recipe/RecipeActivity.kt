package com.hera.giziwise.home.recipe

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.forEach
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
    private lateinit var bottomNavView: BottomNavigationView
    private lateinit var recipeRecyclerView: RecyclerView
    private lateinit var RecipeAdapter: ArticleAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)


        bottomNavView = findViewById(R.id.nav_view)
        recipeRecyclerView = findViewById(R.id.grid_recycler_view)

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
                    true
                }
                R.id.navigation_account -> {
                    openAccountActivity()
                    true
                }
                else -> false
            }
        }

        setIconColors(R.id.navigation_recipes)

//        val textView: TextView = findViewById(R.id.textView)
//        val imageView2: ImageView = findViewById(R.id.imageView2)
//        val imageUrl =
//            "https://assets.ayobandung.com/crop/0x0:0x0/750x500/webp/photo/2023/04/03/Ilustrasi-diet-sehat-3832311830.jpg"
//
//        Glide.with(this)
//            .load(imageUrl)
//            .into(imageView2)
//
//        // Setel teks artikel
//        val articleText =
//            "Ini adalah teks artikel yang panjang. Klik di sini untuk membaca lebih lanjut."
//        val spannableString = SpannableString(articleText)
//
//        // Tambahkan ClickableSpan untuk teks
//        val clickableSpan = object : ClickableSpan() {
//            override fun onClick(widget: View) {
//                openBahanRecipeActivity(imageUrl, getString(R.string.isi_artikel))
//            }
//        }
//        spannableString.setSpan(
//            clickableSpan,
//            0,
//            articleText.length,
//            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//        )
//
//        // Tambahkan ClickableSpan untuk gambar
//        val clickableImageSpan = object : ClickableSpan() {
//            override fun onClick(widget: View) {
//                openDetailArticleActivity(imageUrl, getString(R.string.isi_artikel))
//            }
//        }
//        spannableString.setSpan(clickableImageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

//        // Setel teks dan gambar ke TextView
//        textView.text = spannableString
//        textView.movementMethod = LinkMovementMethod.getInstance()
//        textView.highlightColor = Color.TRANSPARENT  // Menghilangkan highlight saat teks diklik
//        textView.setOnClickListener {
//            openDetailArticleActivity(imageUrl, getString(R.string.isi_artikel))
//        }
//        imageView2.setOnClickListener {
//            openDetailArticleActivity(imageUrl, getString(R.string.isi_artikel))
//        }

        // Inisialisasi RecyclerView dan Adapter untuk artikel
        //RecipeAdapter = ArticleAdapter()
       // recipeRecyclerView.layoutManager =
         //   LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
       // recipeRecyclerView.adapter = RecipeAdapter

        //fetchArticles()
    }

    private fun openBahanRecipeActivity(imageUrl: String?, articleContent: String) {
        val intent = Intent(this, DetailArticleActivity::class.java)
        intent.putExtra("preview_image", imageUrl)
        intent.putExtra("bahan-bahan", articleContent)
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
                    RecipeAdapter.updateData(articles)
                } else {
                    // Tangani kesalahan saat respons API tidak berhasil
                    Toast.makeText(this@RecipeActivity, "Gagal memuat artikel", Toast.LENGTH_SHORT)
                        .show()
                }
            } catch (e: Exception) {
                // Tangani kesalahan saat panggilan API gagal
                Toast.makeText(this@RecipeActivity, "Gagal memuat artikel", Toast.LENGTH_SHORT)
                    .show()
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

    private fun setIconColors(selectedItemId: Int) {
        val menu = bottomNavView.menu
        menu.forEach { item ->
            val icon = item.icon
            if (item.itemId == selectedItemId) {
                icon?.setTintList(ContextCompat.getColorStateList(this, R.color.green))
            } else {
                icon?.setTintList(ContextCompat.getColorStateList(this, R.color.grey))
            }
        }

        // Menemukan ikon artikel dan mengatur TintList-nya menjadi hijau
        val articleIcon = menu.findItem(R.id.navigation_articles)?.icon
        articleIcon?.setTintList(ContextCompat.getColorStateList(this, R.color.green))
    }


}