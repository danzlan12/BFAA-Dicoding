package com.dicoding.picodiploma.fundamentalsubmission1.favorite

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dicoding.picodiploma.fundamentalsubmission1.db.FavoriteUser

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: FavoriteUser)

    @Query("DELETE FROM FavoriteUser WHERE FavoriteUser.id = :id")
    fun delete(id: Int)

    @Query("SELECT * FROM FavoriteUser ORDER BY login ASC")
    fun getAll(): LiveData<List<FavoriteUser>>

    @Query("SELECT * FROM FavoriteUser WHERE FavoriteUser.id = :id")
    fun getId(id: Int): LiveData<FavoriteUser>
}