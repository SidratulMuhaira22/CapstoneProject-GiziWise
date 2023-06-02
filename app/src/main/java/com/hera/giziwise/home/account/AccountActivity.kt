package com.hera.giziwise.home.account

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.hera.giziwise.R

class AccountActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)
    }

    @Override
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.account_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}