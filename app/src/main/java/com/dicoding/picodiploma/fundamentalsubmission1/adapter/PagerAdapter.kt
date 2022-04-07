package com.dicoding.picodiploma.fundamentalsubmission1.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.picodiploma.fundamentalsubmission1.detail.fragment.FollowersFragment
import com.dicoding.picodiploma.fundamentalsubmission1.detail.fragment.FollowingFragment

class PagerAdapter(activity: AppCompatActivity, private var loginUser: String) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowersFragment.newInstance(loginUser)
            1 -> fragment = FollowingFragment.newInstance(loginUser)
        }
        return fragment as Fragment
    }
}