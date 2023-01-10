package com.example.storyapp.remote.respone

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RegisterResponse(
	val error: Boolean,
	val message: String
) : Parcelable