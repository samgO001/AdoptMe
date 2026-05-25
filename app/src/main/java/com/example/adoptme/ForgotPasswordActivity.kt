package com.example.adoptme

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

// Pantalla para recuperar contraseña (simulado)
class ForgotPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        setupToolbar(findViewById(R.id.toolbar), getString(R.string.forgot_title))

        val etEmail = findViewById<EditText>(R.id.etEmail)

        findViewById<Button>(R.id.btnSendLink).setOnClickListener {
            if (!Validation.validateEmail(etEmail)) return@setOnClickListener
            Toast.makeText(this, R.string.forgot_success, Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
