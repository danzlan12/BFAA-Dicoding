package com.dicoding.picodiploma.fundamentalsubmission1.favorite

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.fundamentalsubmission1.databinding.ItemRowUserBinding
import com.dicoding.picodiploma.fundamentalsubmission1.db.FavoriteUser
import com.dicoding.picodiploma.fundamentalsubmission1.detail.DetailUserActivity

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteUserViewHolder>() {

    private val userFavorite = ArrayList<FavoriteUser>()

    class FavoriteUserViewHolder(private val binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favorites: FavoriteUser) {
            with(binding) {
                getUsername.text = favorites.login
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailUserActivity::class.java)
                    intent.putExtra(DetailUserActivity.EXTRA_DATA, favorites.login)
                    itemView.context.startActivity(intent)
                }
            }
            Glide.with(itemView.context)
                .load(favorites.avatarUrl)
                .circleCrop()
                .into(binding.imgAvatar)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setListFavorite(items: List<FavoriteUser>) {
        userFavorite.clear()
        userFavorite.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteUserViewHolder {
        val itemRowUserBinding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteUserViewHolder(itemRowUserBinding)
    }

    override fun onBindViewHolder(holder: FavoriteUserViewHolder, position: Int) {
        val favorites = userFavorite[position]
        holder.bind(favorites)
    }

    override fun getItemCount(): Int = userFavorite.size
}