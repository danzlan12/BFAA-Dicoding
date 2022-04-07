package com.dicoding.picodiploma.fundamentalsubmission1.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.fundamentalsubmission1.detail.User
import com.dicoding.picodiploma.fundamentalsubmission1.databinding.ItemRowUserBinding

class FollowingAdapter (private val listFollowing: List<User>) : RecyclerView.Adapter<FollowingAdapter.ViewHolder>(){

    class ViewHolder(var binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val following = listFollowing[position]

        with(holder.binding) {
            com.bumptech.glide.Glide.with(root.context)
                .load(following.avatarUrl)
                .circleCrop()
                .into(imgAvatar)
            getUsername.text = following.login
        }
    }

    override fun getItemCount(): Int = listFollowing.size
}