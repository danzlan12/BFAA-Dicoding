package com.dicoding.picodiploma.fundamentalsubmission1.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.fundamentalsubmission1.R
import com.dicoding.picodiploma.fundamentalsubmission1.setting.SettingsActivity
import com.dicoding.picodiploma.fundamentalsubmission1.detail.User
import com.dicoding.picodiploma.fundamentalsubmission1.adapter.SearchAdapter
import com.dicoding.picodiploma.fundamentalsubmission1.databinding.ActivityMainBinding
import com.dicoding.picodiploma.fundamentalsubmission1.detail.DetailUserActivity
import com.dicoding.picodiploma.fundamentalsubmission1.favorite.FavoriteUserActivity
import com.dicoding.picodiploma.fundamentalsubmission1.setting.SettingPreferences
import com.dicoding.picodiploma.fundamentalsubmission1.setting.SettingViewModel
import com.dicoding.picodiploma.fundamentalsubmission1.setting.SettingViewModelFactory


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()
    private var listUser = ArrayList<User>()
    private val dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel.listGithubUser.observe(this, { listGithubUser ->
            setUserData(listGithubUser)
        })
        mainViewModel.totalCount.observe(this, {
            setNotice(it)
        })

        mainViewModel.isLoading.observe(this, {
            showLoading(it, binding.progressBar)
        })
        mainViewModel.status.observe(this, { status ->
            status?.let {
                Toast.makeText(this, status.toString(), Toast.LENGTH_SHORT).show()
            }
        })

        binding.btnSend.setOnClickListener { view ->
            mainViewModel.fiturSearch(binding.searchUser.text.toString())
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        val layoutManager = LinearLayoutManager(this@MainActivity)
        binding.rvUser.layoutManager = layoutManager

        getSettingTheme()
    }

    private fun showSelectedUser(user: String) {
        val moveWithParcelableIntent = Intent(this@MainActivity, DetailUserActivity::class.java)
        moveWithParcelableIntent.putExtra(DetailUserActivity.EXTRA_DATA, user)
        startActivity(moveWithParcelableIntent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    binding.rvUser.visibility = View.VISIBLE
                    mainViewModel.fiturSearch(it)
                    setUserData(listUser)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return true
    }
    override fun onOptionsItemSelected(menu: MenuItem): Boolean {
        return when (menu.itemId) {
            R.id.setting -> {
                val intent = Intent(this@MainActivity, SettingsActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.favoriteBtn -> {
                val intent = Intent(this@MainActivity, FavoriteUserActivity::class.java)
                startActivity(intent)
                true
            }
            else -> true
        }
    }
    private fun setUserData(listUserGithub: List<User>) {
        val listUser = ArrayList<User>()
        for (user in listUserGithub) {
            listUser.clear()
            listUser.addAll(listUserGithub)
        }
        val adapter = SearchAdapter(listUser)
        binding.rvUser.adapter = adapter

        adapter.setOnItemClickCallback(object : SearchAdapter.OnItemClickCallback {
            override fun onItemClicked(data: String) {
                showSelectedUser(data)
            }
        })
    }
    private fun setNotice(totalCount: Int) {
        with(binding) {
            if (totalCount == 0) {
                setNotice.visibility = View.VISIBLE
                inputLayout.visibility = View.VISIBLE
                btnSend.visibility = View.VISIBLE
                setNotice.text = resources.getString(R.string.user_not_found)
            } else {
                setNotice.visibility = View.INVISIBLE
                inputLayout.visibility = View.INVISIBLE
                btnSend.visibility = View.INVISIBLE
            }
        }
    }
    private fun showLoading(isLoading: Boolean, view: View) {
        if (isLoading) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.INVISIBLE
        }
    }

    private fun getSettingTheme(){
        val pref = SettingPreferences.getInstance(dataStore)
        val viewModel = ViewModelProvider(
            this,
            SettingViewModelFactory(pref)
        )[SettingViewModel::class.java]

        viewModel.getThemeSettings().observe(this,
            { isDarkModeActive: Boolean ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            })
    }

}