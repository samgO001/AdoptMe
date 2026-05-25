package com.example.adoptme

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

// Pantalla para crear cuenta
class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        setupToolbar(findViewById(R.id.toolbar), getString(R.string.register))

        val etName = findViewById<EditText>(R.id.etName)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val etConfirm = findViewById<EditText>(R.id.etConfirmPassword)

        findViewById<Button>(R.id.btnRegister).setOnClickListener {
            val ok = Validation.validateName(etName) &&
                Validation.validateEmail(etEmail) &&
                Validation.validatePassword(etPassword)

            if (!ok) return@setOnClickListener

            if (etPassword.text.toString() != etConfirm.text.toString()) {
                etConfirm.error = getString(R.string.error_password_mismatch)
                return@setOnClickListener
            }

            Toast.makeText(this, R.string.register_success, Toast.LENGTH_SHORT).show()
            Navigation.openLogin(this)
            finish()
        }

        findViewById<TextView>(R.id.tvGoLogin).setOnClickListener {
            Navigation.openLogin(this)
            finish()
        }
    }
}
