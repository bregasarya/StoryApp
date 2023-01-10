package com.example.storyapp.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.storyapp.remote.respone.ListStoryItem
import com.example.storyapp.remote.retrofit.ApiService
import com.example.storyapp.session.LoginPreferences

class StoryPagingSource(private val LoginPreferences: LoginPreferences, private val apiService: ApiService): PagingSource<Int, ListStoryItem>(){

    override fun getRefreshKey(state: PagingState<Int, ListStoryItem>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListStoryItem> {
        return try{
            val page = params.key ?: INITIAL_PAGE
            val token = LoginPreferences.getUser().token.toString()

            if (token.isNotEmpty()){
                val dataResponse = token.let{ apiService.getStories("Bearer $it", page, params.loadSize, 0)}
                if (dataResponse.isSuccessful){
                    LoadResult.Page(
                        data = dataResponse.body()?.listStory ?: emptyList(),
                        prevKey = if (page == INITIAL_PAGE) null else page -1,
                        nextKey = if (dataResponse.body()?.listStory.isNullOrEmpty()) null else page + 1
                    )
                } else {
                    LoadResult.Error(Exception("Gagal Memuat List Story"))
                }
            } else {
                LoadResult.Error(Exception("Token Tidak Ada"))
            }
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    private companion object {
        const val INITIAL_PAGE = 1
    }

}