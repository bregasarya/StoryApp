package com.example.storyapp.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.storyapp.data.Result
import com.example.storyapp.ui.main.MainActivity
import com.example.storyapp.ui.register.RegisterActivity
import com.example.storyapp.session.LoginPreferences
import com.example.storyapp.factory.ViewModelFactory
import com.example.storyapp.databinding.ActivityLoginBinding
import com.example.storyapp.remote.respone.LoginResponse
import com.example.storyapp.remote.respone.LoginResultModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private  val loginViewModel: LoginViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)

        showLoading(false)
        setupAnimation()
        setViewModel()
        binding.loginButton.setOnClickListener {
            buttonClicked()
        }
        binding.registerButton.setOnClickListener {
            toRegister()
        }
    }

    private fun setViewModel(){
        viewModelFactory = ViewModelFactory.getInstnce(binding.root.context)
    }

    private fun toRegister() {
        startActivity(Intent(this, RegisterActivity::class.java))
        finish()
    }

    private fun buttonClicked() {
        val email = binding.edEmail.text.toString()
        val password = binding.edPassword.text.toString()

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            processLogin(email, password)
        } else {
            Toast.makeText(this, "Please Input Your Email and Password", Toast.LENGTH_SHORT).show()
        }
    }

    private fun processLogin(email: String, password:String){
        loginViewModel.postLogin(email, password).observe(this){
            if (it != null){
                when(it){
                    is Result.Loading ->{
                        showLoading(true)
                    }
                    is Result.Error -> {
                        showLoading(false)
                        Toast.makeText(this, "Login Failed Check Your Email or Password", Toast.LENGTH_SHORT).show()
                    }
                    is Result.Success -> {
                        succesLogin(it.data)
                        Toast.makeText(this, "Welcome ${it.data.loginResult?.name}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressbar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun intentToHome(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun succesLogin(responseLogin: LoginResponse){
        saveDataUser(responseLogin)
        intentToHome()
    }

    private fun saveDataUser(responseLogin: LoginResponse){
        val preferenceLogin = LoginPreferences(this)
        val resultLogin = responseLogin.loginResult
        val loginResultModel = LoginResultModel(
            name = resultLogin?.name, userId =  resultLogin?.userId, token = resultLogin?.token
        )
        preferenceLogin.setAuthLogin(loginResultModel)
    }

    private fun setupAnimation() {

        val image = ObjectAnimator.ofFloat(binding.imageView, View.ALPHA, 1f).setDuration(500)
        val tvSignup = ObjectAnimator.ofFloat(binding.tvSignUp, View.ALPHA, 1f).setDuration(500)
        val edemail = ObjectAnimator.ofFloat(binding.emailTextInputLayout, View.ALPHA, 1f).setDuration(500)
        val edpassword = ObjectAnimator.ofFloat(binding.passwordTextInputLayout, View.ALPHA, 1f).setDuration(500)
        val btnLogin = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(500)
        val btnRegister = ObjectAnimator.ofFloat(binding.registerButton, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(
                image,
                AnimatorSet().apply { playTogether(tvSignup, edemail, edpassword) },
                AnimatorSet().apply { playTogether(btnLogin, btnRegister) },
            )
            start()
        }
    }
}