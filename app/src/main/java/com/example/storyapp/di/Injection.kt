package com.example.storyapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.storyapp.data.StoryRepository
import com.example.storyapp.remote.retrofit.ApiConfig
import com.example.storyapp.session.LoginPreferences

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("story")

object Injection {
    fun provideRepository(context: Context): StoryRepository {
        val prefe = LoginPreferences(context)
        val apiService = ApiConfig.getApiService()
        return StoryRepository.getInstance(prefe, apiService)
    }
}