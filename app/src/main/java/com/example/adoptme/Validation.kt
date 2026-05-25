package com.example.adoptme

import android.widget.EditText

// Revisa los campos de los formularios antes de continuar
object Validation {

    fun isValidEmail(email: String): Boolean =
        email.contains("@") && email.contains(".") && email.length >= 5

    fun validateEmail(editText: EditText): Boolean {
        val email = editText.text.toString().trim()
        return when {
            email.isEmpty() -> {
                editText.error = editText.context.getString(R.string.error_email_empty)
                false
            }
            !isValidEmail(email) -> {
                editText.error = editText.context.getString(R.string.error_email_invalid)
                false
            }
            else -> {
                editText.error = null
                true
            }
        }
    }

    fun validatePassword(editText: EditText, minLength: Int = 4): Boolean {
        val password = editText.text.toString()
        return when {
            password.isEmpty() -> {
                editText.error = editText.context.getString(R.string.error_password_empty)
                false
            }
            password.length < minLength -> {
                editText.error = editText.context.getString(R.string.error_password_short)
                false
            }
            else -> {
                editText.error = null
                true
            }
        }
    }

    fun validateName(editText: EditText): Boolean {
        val name = editText.text.toString().trim()
        return if (name.isEmpty()) {
            editText.error = editText.context.getString(R.string.error_name_empty)
            false
        } else {
            editText.error = null
            true
        }
    }
}
