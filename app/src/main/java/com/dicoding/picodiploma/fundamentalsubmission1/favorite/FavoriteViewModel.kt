package com.dicoding.picodiploma.fundamentalsubmission1.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.fundamentalsubmission1.db.FavoriteUser

class FavoriteViewModel(application: Application) : ViewModel() {

    private val FavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun getFavorite(): LiveData<List<FavoriteUser>> = FavoriteRepository.getFavorite()
}