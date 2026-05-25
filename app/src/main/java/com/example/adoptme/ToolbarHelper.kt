package com.example.adoptme

import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar

// Barra de arriba con título y flecha para volver (se usa en login, registro, etc.)
fun AppCompatActivity.setupToolbar(toolbar: MaterialToolbar, title: String) {
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    supportActionBar?.title = title
    toolbar.setNavigationOnClickListener {
        onBackPressedDispatcher.onBackPressed()
    }
}
