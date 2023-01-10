package com.example.storyapp.remote.respone

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class GetAllStoryResponse(

	@field:SerializedName("listStory")
	val listStory: ArrayList<ListStoryItem>,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
) : Parcelable

@Parcelize
data class ListStoryItem(

	@field:SerializedName("name")
	var name: String?,

	@field:SerializedName("description")
	var description: String?,

	@field:SerializedName("photoUrl")
	var photoUrl: String?,

	@field:SerializedName("createdAt")
	var createdAt: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("lat")
	val lat: Double,

	@field:SerializedName("lon")
	val lon: Double,

	) : Parcelable
