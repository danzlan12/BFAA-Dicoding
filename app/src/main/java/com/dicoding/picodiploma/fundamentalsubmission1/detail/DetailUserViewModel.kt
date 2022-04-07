package com.dicoding.picodiploma.fundamentalsubmission1.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.fundamentalsubmission1.favorite.FavoriteRepository
import com.dicoding.picodiploma.fundamentalsubmission1.api.ApiConfig
import com.dicoding.picodiploma.fundamentalsubmission1.db.FavoriteUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel (application: Application): ViewModel(){

    private val _listDetail = MutableLiveData<DetailUserResponse>()
    val listDetail: LiveData<DetailUserResponse> = _listDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val FavRepository: FavoriteRepository = FavoriteRepository(application)
    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status

    fun insert(user: FavoriteUser) {
        FavRepository.insert(user)
    }
    fun delete(id: Int) {
        FavRepository.delete(id)
    }
    fun getFavorite(): LiveData<List<FavoriteUser>> =
        FavRepository.getFavorite()

    internal fun getUser(login: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(login)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listDetail.value = response.body()
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
    companion object {
        private const val TAG = "DetailUserViewModel"
    }
}