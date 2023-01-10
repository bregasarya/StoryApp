package com.example.storyapp.ui.register

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
import com.example.storyapp.factory.ViewModelFactory
import com.example.storyapp.databinding.ActivityRegisterBinding
import com.example.storyapp.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private val registerViewModel: RegisterViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setViewModel()
        showLoading(false)
        setupAnimation()

        binding.regisButton.setOnClickListener {
            toLogin()
            sendDataUser()
        }
    }

    private fun setViewModel() {
        viewModelFactory = ViewModelFactory.getInstnce(binding.root.context)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressbar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun toLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun sendDataUser() {
        val name = binding.edName.text.toString().trim()
        val email = binding.edEmail.text.toString().trim()
        val password = binding.edPassword.text.toString().trim()

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            processRegister(name, email, password)
        } else {
            Toast.makeText(this, "Input Your Data", Toast.LENGTH_SHORT).show()
        }
    }

    private fun formRegister() {
        binding.edName.text?.clear()
        binding.edEmail.text?.clear()
        binding.edPassword.text?.clear()
    }

    private fun processRegister(name: String, email: String, password: String) {
        registerViewModel.postRegister(name, email, password).observe(this) {
            if (it != null) {
                when (it) {
                    is Result.Loading ->{
                        showLoading(true)
                    }
                    is Result.Error -> {
                        showLoading(false)
                        Toast.makeText(this, "There Is an Error", Toast.LENGTH_SHORT).show()
                    }
                    is Result.Success -> {
                        formRegister()
                        startActivity(Intent(this, LoginActivity::class.java))
                        Toast.makeText(this, "BAccount Register Success ${it.data.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun setupAnimation() {

        val image = ObjectAnimator.ofFloat(binding.imageView, View.ALPHA, 1f).setDuration(500)
        val tvSignup = ObjectAnimator.ofFloat(binding.tvSignUp, View.ALPHA, 1f).setDuration(500)
        val edname = ObjectAnimator.ofFloat(binding.edName, View.ALPHA, 1f).setDuration(500)
        val edemail = ObjectAnimator.ofFloat(binding.emailTextInputLayout, View.ALPHA, 1f).setDuration(500)
        val edpassword = ObjectAnimator.ofFloat(binding.passwordTextInputLayout, View.ALPHA, 1f).setDuration(500)
        val btnRegister2 = ObjectAnimator.ofFloat(binding.regisButton, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(
                image,
                AnimatorSet().apply { playTogether(tvSignup, edname, edemail, edpassword) },
                btnRegister2,
            )
            start()
        }
    }
}