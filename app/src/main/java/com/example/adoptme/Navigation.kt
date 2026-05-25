package com.example.adoptme

import android.app.Activity
import android.content.Context
import android.content.Intent

// Funciones para cambiar de pantalla (Intent)
object Navigation {

    fun openHome(context: Context, clearStack: Boolean = false) {
        val intent = Intent(context, MainActivity::class.java)
        if (clearStack) {
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        } else {
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        context.startActivity(intent)
        if (context is Activity && clearStack) context.finish()
    }

    fun openLogin(context: Context) {
        context.startActivity(Intent(context, LoginActivity::class.java))
    }

    fun openRegister(context: Context) {
        context.startActivity(Intent(context, RegisterActivity::class.java))
    }

    fun openForgotPassword(context: Context) {
        context.startActivity(Intent(context, ForgotPasswordActivity::class.java))
    }
}
