package com.hera.giziwise.home

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.hera.giziwise.R
import com.hera.giziwise.home.camera.CameraActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    private lateinit var bottomNavView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

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
                R.id.navigation_account -> {
                    openAccountActivity()
                    true
                }
                else -> false
            }
        }

        // Set the initial icon colors
        setIconColors(R.id.navigation_home)

        bottomNavView.setOnNavigationItemReselectedListener { item ->
            // Handle reselection of icons
            when (item.itemId) {
                R.id.navigation_home -> {
                    // Do something when home icon is reselected
                }
                R.id.navigation_camera -> {
                    // Do something when camera icon is reselected
                }
                R.id.navigation_account -> {
                    // Do something when profile icon is reselected
                }
            }
        }
    }

    private fun openHomeActivity() {
        // Do something when home icon is clicked
        setIconColors(R.id.navigation_home) // Set the home icon to green
    }

    private fun openCameraActivity() {
        val intent = Intent(this, CameraActivity::class.java)
        startActivity(intent)
    }

    private fun openAccountActivity() {
        // Do something when profile icon is clicked
    }

    private fun setIconColors(selectedItemId: Int) {
        val menu = bottomNavView.menu
        for (i in 0 until menu.size()) {
            val item = menu.getItem(i)
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
        // Set the home icon to green
        setIconColors(R.id.navigation_home)
    }
}







