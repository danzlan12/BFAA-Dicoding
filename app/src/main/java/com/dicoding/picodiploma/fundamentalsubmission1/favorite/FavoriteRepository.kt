package com.dicoding.picodiploma.fundamentalsubmission1.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.picodiploma.fundamentalsubmission1.db.FavoriteUser
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository (application : Application) {

    private val favoriteDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoritRoomDatabase.getDatabase(application)
        favoriteDao = db.favoriteUserDao()
    }

    fun getFavorite(): LiveData<List<FavoriteUser>> = favoriteDao.getAll()
    fun insert(user: FavoriteUser) {
        executorService.execute { favoriteDao.insert(user) }
    }
    fun delete(user: Int) {
        executorService.execute { favoriteDao.delete(user) }
    }
}