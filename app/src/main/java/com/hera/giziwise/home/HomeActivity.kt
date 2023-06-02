package com.hera.giziwise.home

<<<<<<< HEAD
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

        setIconColors(R.id.navigation_home)

        bottomNavView.setOnNavigationItemReselectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {

                }
                R.id.navigation_camera -> {

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

    private fun openAccountActivity() {

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

        setIconColors(R.id.navigation_home)
    }
}







=======
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hera.giziwise.R

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }
}
>>>>>>> fb88542102a69986fc981672bbfbd5a22ff8583c
