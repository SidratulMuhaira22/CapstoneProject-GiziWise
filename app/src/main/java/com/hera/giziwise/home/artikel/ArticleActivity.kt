package com.hera.giziwise.home.artikel

import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.view.forEach
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hera.giziwise.R
import com.hera.giziwise.home.account.AccountActivity
import com.hera.giziwise.home.camera.CameraActivity
import com.hera.giziwise.home.recipe.RecipeActivity

class ArticleActivity : AppCompatActivity() {
    private lateinit var bottomNavView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)

        bottomNavView = findViewById(R.id.nav_view)

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
                icon?.setTintList(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            this,
                            R.color.green
                        )
                    )
                )
            } else {
                icon?.setTintList(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            this,
                            R.color.green
                        )
                    )
                )
            }
        }
    }
}
