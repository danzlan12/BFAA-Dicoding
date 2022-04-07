package com.dicoding.picodiploma.fundamentalsubmission1.detail.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.fundamentalsubmission1.detail.model.FollowersViewModel
import com.dicoding.picodiploma.fundamentalsubmission1.detail.User
import com.dicoding.picodiploma.fundamentalsubmission1.adapter.FollowersAdapter
import com.dicoding.picodiploma.fundamentalsubmission1.databinding.FragmentFollowersBinding

class FollowersFragment : Fragment() {

    private lateinit var binding: FragmentFollowersBinding
    private lateinit var followerViewModel: FollowersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        followerViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[FollowersViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        followerViewModel.isLoading.observe(viewLifecycleOwner, {
            showLoading(it, binding.progressBar3)
        })
        followerViewModel.listFollower.observe(viewLifecycleOwner, { listFollower ->
            setDataToFragment(listFollower)
        })
        followerViewModel.status.observe(viewLifecycleOwner, { status ->
            status?.let {
                Toast.makeText(activity, status.toString(), Toast.LENGTH_SHORT).show()
            }
        })
        val getUsername = arguments?.getString(ARG_USERNAME)

        if (getUsername != null) {
            followerViewModel.getFollower(getUsername)
        }

    }

    private fun setDataToFragment(listFollower: List<User>) {
        val listUser = ArrayList<User>()
        with(binding) {
            for (user in listFollower) {
                listUser.clear()
                listUser.addAll(listFollower)
            }
            rvFollower.layoutManager = LinearLayoutManager(context)
            val adapter = FollowersAdapter(listFollower)
            rvFollower.adapter = adapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding
    }

    private fun showLoading(isLoading: Boolean, view: View) {
        if (isLoading) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.INVISIBLE
        }
    }

    companion object {
        private const val ARG_USERNAME = "username"

        fun newInstance(loginUser: String?): FollowersFragment {
            val fragment = FollowersFragment()
            val bundle = Bundle()
            bundle.putString(ARG_USERNAME, loginUser)
            fragment.arguments = bundle
            return fragment
        }
    }
}