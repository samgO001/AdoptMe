package com.example.adoptme

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

// Pantalla de iniciar sesión
class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setupToolbar(findViewById(R.id.toolbar), getString(R.string.login_title))

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)

        findViewById<Button>(R.id.btnLogin).setOnClickListener {
            if (!Validation.validateEmail(etEmail) || !Validation.validatePassword(etPassword)) {
                return@setOnClickListener
            }
            val email = etEmail.text.toString().trim()
            SessionManager.login(this, email)
            Toast.makeText(this, R.string.login_success, Toast.LENGTH_SHORT).show()
            Navigation.openHome(this)
            finish()
        }

        // Google de prueba (no es login real todavía)
        findViewById<Button>(R.id.btnGoogle).setOnClickListener {
            SessionManager.login(this, "usuario@gmail.com")
            Toast.makeText(this, R.string.login_success, Toast.LENGTH_SHORT).show()
            Navigation.openHome(this)
            finish()
        }

        findViewById<TextView>(R.id.tvRegister).setOnClickListener {
            Navigation.openRegister(this)
        }

        findViewById<TextView>(R.id.tvForgotPassword).setOnClickListener {
            Navigation.openForgotPassword(this)
        }

        findViewById<TextView>(R.id.tvBrand).setOnClickListener {
            Navigation.openHome(this)
        }
    }
}
