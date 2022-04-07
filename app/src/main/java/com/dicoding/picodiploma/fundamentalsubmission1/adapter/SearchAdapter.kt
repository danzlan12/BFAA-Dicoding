package com.dicoding.picodiploma.fundamentalsubmission1.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.fundamentalsubmission1.detail.User
import com.dicoding.picodiploma.fundamentalsubmission1.databinding.ItemRowUserBinding

class SearchAdapter(private val listUser: List<User>) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    class ViewHolder(var binding:  ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = listUser[position]

        with(holder.binding) {
            Glide.with(root.context)
                .load(user.avatarUrl)
                .circleCrop()
                .into(imgAvatar)
            getUsername.text = user.login
            root.setOnClickListener { onItemClickCallback.onItemClicked(listUser[position].login) }
        }
    }

    override fun getItemCount(): Int = listUser.size

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: String)
    }
}