package com.hera.giziwise.home.account

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.hera.giziwise.R
import com.hera.giziwise.databinding.ActivityAccountBinding
import com.hera.giziwise.home.HomeActivity
import com.hera.giziwise.home.artikel.ArticleActivity
import com.hera.giziwise.home.camera.CameraActivity
import com.hera.giziwise.home.recipe.RecipeActivity
import com.hera.giziwise.login.LoginActivity

class AccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAccountBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        sharedPreferences = getSharedPreferences("login_status", Context.MODE_PRIVATE)

        binding.btnKeluar.setOnClickListener {
            firebaseAuth.signOut()

            // Hapus status login dari Shared Preferences
            val editor = sharedPreferences.edit()
            editor.putBoolean("is_logged_in", false)
            editor.apply()

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        // NavigationBar
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavigationView.selectedItemId = R.id.navigation_account
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
                R.id.navigation_recipes-> {
                    startActivity(Intent(applicationContext, RecipeActivity::class.java))
                    overridePendingTransition(R.anim.slide_right, R.anim.slide_left)
                    finish()
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_account -> return@setOnItemSelectedListener true
            }
            false
        }
    }

    @Override
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.account_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}