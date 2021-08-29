package com.studying.mvistudyingapp.api

import androidx.lifecycle.LiveData
import com.studying.mvistudyingapp.model.BlogPost
import com.studying.mvistudyingapp.model.User
import com.studying.mvistudyingapp.util.GenericApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("placeholder/user/{userId}")
    fun getUser(@Path("userId") userId: String): LiveData<GenericApiResponse<User>>

    @GET("placeholder/blogs")
    fun getBlogPosts(): LiveData<GenericApiResponse<List<BlogPost>>>
}