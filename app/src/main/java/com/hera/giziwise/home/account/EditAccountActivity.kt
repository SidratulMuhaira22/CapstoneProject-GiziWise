package com.hera.giziwise.home.account

import android.content.Context
import android.graphics.Canvas
import android.os.Bundle
import android.util.AttributeSet
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.hera.giziwise.R

class EditAccountActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editaccount)
    }

    @Override
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.editaccount_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }


}