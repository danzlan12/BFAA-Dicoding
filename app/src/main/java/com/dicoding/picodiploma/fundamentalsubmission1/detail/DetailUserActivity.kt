package com.dicoding.picodiploma.fundamentalsubmission1.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.fundamentalsubmission1.R
import com.dicoding.picodiploma.fundamentalsubmission1.ViewModelFactory
import com.dicoding.picodiploma.fundamentalsubmission1.adapter.PagerAdapter
import com.dicoding.picodiploma.fundamentalsubmission1.databinding.ActivityDetailUserBinding
import com.dicoding.picodiploma.fundamentalsubmission1.db.FavoriteUser
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var detailUserViewModel : DetailUserViewModel
    private var detailUser = DetailUserResponse()
    private var favoriteUser: FavoriteUser? = null
    private var buttonState: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val userGet = intent.getStringExtra(EXTRA_DATA)
        detailUserViewModel = obtainViewModel(this@DetailUserActivity)
        detailUserViewModel.isLoading.observe(this, {
            showLoading(it, binding.progressBar2)
        })
        detailUserViewModel.status.observe(this, { status ->
            status?.let {
                Toast.makeText(this, status.toString(), Toast.LENGTH_SHORT).show()
            }
        })
        detailUserViewModel.getUser(userGet.toString())
        setTabLayoutView(userGet.toString())

        detailUserViewModel.listDetail.observe(this, { detailList ->
            detailUser = detailList
            setDataToView(detailUser)
            favoriteUser = FavoriteUser(detailUser.id, detailUser.login)
            detailUserViewModel.getFavorite().observe(this, { favoriteList ->
                if (favoriteList != null) {
                    for (data in favoriteList) {
                        if (detailUser.id == data.id) {
                            buttonState = true
                            binding.btnFavorite.setImageResource(R.drawable.ic_favorite_24)
                        }
                    }
                }
            })
            binding.btnFavorite.setOnClickListener {
                if (!buttonState) {
                    buttonState = true
                    binding.btnFavorite.setImageResource(R.drawable.ic_favorite_24)
                    insertToDatabase(detailUser)
                } else {
                    buttonState = false
                    binding.btnFavorite.setImageResource(R.drawable.ic_favorite_border_24)
                    detailUserViewModel.delete(detailUser.id)
                    Toast.makeText(this,
                        "Success Delete", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailUserViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[DetailUserViewModel::class.java]
    }

    private fun insertToDatabase(detailList: DetailUserResponse) {
        favoriteUser.let { favUser ->
            favUser?.id = detailList.id
            favUser?.login = detailList.login
            favUser?.avatarUrl = detailList.avatarUrl
            detailUserViewModel.insert(favoriteUser as FavoriteUser)
            Toast.makeText(this,
                "Success Add Favorite", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setTabLayoutView(login : String) {
        val sectionPagerAdapter = PagerAdapter(this, login)
        val viewPager: ViewPager2 = binding.viewPager

        viewPager.adapter = sectionPagerAdapter
        val tabs: TabLayout = binding.tabs
        val tabTitle = resources.getStringArray(R.array.tab_title)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = tabTitle[position]
        }.attach()
    }

    private fun setDataToView(detailList: DetailUserResponse) {
        binding.apply {
            Glide.with(this@DetailUserActivity)
                .load(detailList.avatarUrl)
                .circleCrop()
                .into(imageProfile)
            detailsTvName.text = detailList.name ?: "Name not found"
            detailsTvUsername.text = detailList.login
            detailsTvFollower.text = resources.getString(R.string.follower, detailList.followers)
            detailsTvFollowing.text = resources.getString(R.string.following, detailList.following)
            detailsTvRepository.text = resources.getString(R.string.repository, detailList.publicRepos)
            detailsTvCompany.text = detailList.company ?: "-"
            detailsTvLocation.text = detailList.location ?: "-"
        }
        supportActionBar?.title = resources.getString(R.string.name_profile, detailList.login)
    }
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
    private fun showLoading(isLoading: Boolean, view: View) {
        if (isLoading) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.INVISIBLE
        }
    }
    companion object{
        const val EXTRA_DATA = "extra_data"
    }
}