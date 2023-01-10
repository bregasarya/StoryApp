package com.example.storyapp.ui.main

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.storyapp.data.StoryRepository
import com.example.storyapp.remote.respone.ListStoryItem

class MainViewModel(storyRepository: StoryRepository): ViewModel() {
    val getAllStory: LiveData<PagingData<ListStoryItem>> =
        storyRepository.getListStory().cachedIn(viewModelScope)
}