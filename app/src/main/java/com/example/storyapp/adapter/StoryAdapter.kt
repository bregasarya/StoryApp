package com.example.storyapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.storyapp.remote.respone.ListStoryItem
import com.example.storyapp.databinding.ItemRowStoryBinding

class StoryAdapter: PagingDataAdapter<ListStoryItem, StoryAdapter.StoryViewHolder>(DIFF_CALLBACK)  {

    class  StoryViewHolder(val binding: ItemRowStoryBinding): RecyclerView.ViewHolder(binding.root)

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryAdapter.StoryViewHolder {
        return StoryViewHolder(ItemRowStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: StoryAdapter.StoryViewHolder, position: Int) {
        val listStory = getItem(position)
        holder.binding.apply {
            holder.itemView.setOnClickListener{
                if (listStory != null){
                    onItemClickCallback.onItemClicked(listStory)
                }
            }
            tvName.text = listStory?.name
            tvDate.text = listStory?.createdAt.toString()
            tvDescription.text = listStory?.description
            Glide.with(ivStory.context)
                .load(listStory?.photoUrl)
                .fitCenter()
                .into(ivStory)
        }
    }

    interface OnItemClickCallback{
        fun onItemClicked(data: ListStoryItem)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ListStoryItem,
                newItem: ListStoryItem
            ): Boolean {
                return  oldItem.id == newItem.id &&
                        oldItem.name == newItem.name &&
                        oldItem.photoUrl == newItem.photoUrl &&
                        oldItem.createdAt == newItem.createdAt &&
                        oldItem.description == newItem.description
            }
        }
    }
}