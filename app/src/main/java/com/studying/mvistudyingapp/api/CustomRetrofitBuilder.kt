package com.studying.mvistudyingapp.api

import com.studying.mvistudyingapp.util.Constants.Companion.BASE_URL
import com.studying.mvistudyingapp.util.LiveDataCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CustomRetrofitBuilder {
    val retrofitBuilder: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
    }
    val apiService: ApiService by lazy {
        retrofitBuilder.build()
            .create(ApiService::class.java)
    }
}