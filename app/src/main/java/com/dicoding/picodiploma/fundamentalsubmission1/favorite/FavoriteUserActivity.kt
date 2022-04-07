package com.dicoding.picodiploma.fundamentalsubmission1.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.fundamentalsubmission1.R
import com.dicoding.picodiploma.fundamentalsubmission1.ViewModelFactory
import com.dicoding.picodiploma.fundamentalsubmission1.databinding.ActivityFavoriteUserBinding

class FavoriteUserActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFavoriteUserBinding
    private lateinit var adapter: FavoriteAdapter
    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.favorite_user)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setUserFavorite()
        adapter = FavoriteAdapter()
        binding.rvFavorites.layoutManager = LinearLayoutManager(this)
        binding.rvFavorites.setHasFixedSize(false)
        binding.rvFavorites.adapter = adapter
    }
    private fun setUserFavorite() {
        favoriteViewModel = obtainViewModel(this@FavoriteUserActivity)
        favoriteViewModel.getFavorite().observe(this@FavoriteUserActivity, { favList ->
            if (favList !=null){
                adapter.setListFavorite(favList)
            }
        })
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteViewModel::class.java]
    }

    override fun onDestroy() {
        super.onDestroy()
        binding
    }
}