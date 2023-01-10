package com.example.storyapp.remote.respone

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginResponse(
    val error: Boolean,
    val message: String,
    val loginResult: LoginResult? = null
) : Parcelable

@Parcelize
data class LoginResult(
    val userId: String,
    val name: String,
    val token: String
) : Parcelable
