package com.dicoding.picodiploma.fundamentalsubmission1.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.fundamentalsubmission1.detail.User
import com.dicoding.picodiploma.fundamentalsubmission1.detail.UserResponse
import com.dicoding.picodiploma.fundamentalsubmission1.api.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _listGithubUser = MutableLiveData<List<User>>()
    val listGithubUser: LiveData<List<User>> = _listGithubUser
    private val _isLoading = MutableLiveData<Boolean>()
    private val _totalCount = MutableLiveData<Int>()
    val totalCount: LiveData<Int> = _totalCount
    val isLoading: LiveData<Boolean> = _isLoading
    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status

    internal fun fiturSearch (query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(query)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listGithubUser.value = response.body()?.Users
                        _totalCount.value = response.body()?.totalCount
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
                _status.value = "Data failed to load, please try again."
            }
        })
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}