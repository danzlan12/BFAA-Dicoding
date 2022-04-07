package com.dicoding.picodiploma.fundamentalsubmission1.favorite

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.picodiploma.fundamentalsubmission1.db.FavoriteUser

@Database(entities = [FavoriteUser::class], version = 5)
abstract class FavoritRoomDatabase : RoomDatabase() {

    abstract fun favoriteUserDao(): FavoriteDao

    companion object {
        @Volatile
        private var INSTANCE: FavoritRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): FavoritRoomDatabase {
            if (INSTANCE == null) {
                synchronized(FavoritRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        FavoritRoomDatabase::class.java, "favorite_user"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE as FavoritRoomDatabase
        }
    }
}