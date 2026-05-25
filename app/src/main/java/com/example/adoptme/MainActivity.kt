package com.example.adoptme

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.widget.ScrollView
import com.google.android.material.bottomnavigation.BottomNavigationView

// Pantalla principal - es la que abre al iniciar la app
class MainActivity : AppCompatActivity() {

    private lateinit var bottomNav: BottomNavigationView
    private lateinit var scrollHome: ScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        scrollHome = findViewById(R.id.scrollHome)
        bottomNav = findViewById(R.id.bottomNav)

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    scrollHome.smoothScrollTo(0, 0)
                    true
                }
                R.id.nav_login -> {
                    Navigation.openLogin(this)
                    false
                }
                R.id.nav_register -> {
                    Navigation.openRegister(this)
                    false
                }
                else -> false
            }
        }

        findViewById<ImageView>(R.id.imgLogo).setOnClickListener {
            scrollHome.smoothScrollTo(0, 0)
        }

        findViewById<Button>(R.id.btnRegister).setOnClickListener {
            Navigation.openRegister(this)
        }
        findViewById<Button>(R.id.btnGoLogin).setOnClickListener {
            Navigation.openLogin(this)
        }

        val email = SessionManager.getEmail(this)
        if (SessionManager.isLoggedIn(this) && email != null) {
            Toast.makeText(
                this,
                getString(R.string.welcome_user, email.substringBefore("@")),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onResume() {
        super.onResume()
        bottomNav.selectedItemId = R.id.nav_home
    }
}
