package com.dicoding.picodiploma.fundamentalsubmission1.api

import com.dicoding.picodiploma.fundamentalsubmission1.BuildConfig
import com.dicoding.picodiploma.fundamentalsubmission1.detail.DetailUserResponse
import com.dicoding.picodiploma.fundamentalsubmission1.detail.User
import com.dicoding.picodiploma.fundamentalsubmission1.detail.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    @Headers("Authorization: token ${BuildConfig.API_KEY}")
    fun getUser(
        @Query("q") login: String
    ): Call<UserResponse>

    @GET("users/{login}")
    @Headers("Authorization: token ${BuildConfig.API_KEY}")
    fun getDetailUser(
        @Path("login") login: String
    ): Call<DetailUserResponse>

    @GET("users/{login}/followers")
    @Headers("Authorization: token ${BuildConfig.API_KEY}")
    fun getFollower(
        @Path("login") login: String
    ): Call<List<User>>

    @GET("users/{login}/following")
    @Headers("Authorization: token ${BuildConfig.API_KEY}")
    fun getFollowing(
        @Path("login") login: String
    ): Call<List<User>>
}