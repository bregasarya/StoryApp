package com.example.storyapp.ui.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.storyapp.R
import com.example.storyapp.remote.respone.LoginResultModel
import com.example.storyapp.session.LoginPreferences
import com.example.storyapp.ui.login.LoginActivity
import com.example.storyapp.ui.main.MainActivity

@SuppressLint("CustomSplashScreen")

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var loginPreferences: LoginPreferences
    private lateinit var loginResultModel: LoginResultModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        loginPreferences = LoginPreferences(this)
        loginResultModel = loginPreferences.getUser()
        setActionBar()
        isLogin()

    }

    private fun setActionBar(){
        supportActionBar?.hide()
    }

    private fun process(intent: Intent) {
        val splashTimer: Long = 3000
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(intent)
            finish()
        }, splashTimer)
    }

    private fun isLogin() {
        if (loginResultModel.name != null && loginResultModel.token != null && loginResultModel.userId != null) {
            val intent = Intent(this, MainActivity::class.java)
            process(intent)
        } else {
            val intent = Intent(this, LoginActivity::class.java)
            process(intent)
        }
    }
}