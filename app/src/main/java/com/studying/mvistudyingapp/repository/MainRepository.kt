package com.studying.mvistudyingapp.repository

import androidx.lifecycle.LiveData
import com.studying.mvistudyingapp.api.CustomRetrofitBuilder
import com.studying.mvistudyingapp.model.BlogPost
import com.studying.mvistudyingapp.model.User
import com.studying.mvistudyingapp.ui.main.state.MainViewState
import com.studying.mvistudyingapp.util.ApiSuccessResponse
import com.studying.mvistudyingapp.util.DataState
import com.studying.mvistudyingapp.util.GenericApiResponse

object MainRepository {

    fun getBlogPosts(): LiveData<DataState<MainViewState>> {
        return object : NetworkBoundResource<List<BlogPost>, MainViewState>() {
            override fun handleApiSuccessResponse(response: ApiSuccessResponse<List<BlogPost>>) {
                result.value = DataState.success(
                    message = "Success",
                    data = MainViewState(blogPosts = response.body)
                )
            }

            override fun createCall(): LiveData<GenericApiResponse<List<BlogPost>>> {
                return CustomRetrofitBuilder.apiService.getBlogPosts()
            }

        }.asLiveData()
    }

    fun getUser(userId: String): LiveData<DataState<MainViewState>> {

        return object : NetworkBoundResource<User, MainViewState>() {
            override fun handleApiSuccessResponse(response: ApiSuccessResponse<User>) {
                result.value = DataState.success(
                    message = "Success",
                    data = MainViewState(user = response.body)
                )
            }

            override fun createCall(): LiveData<GenericApiResponse<User>> {
                return CustomRetrofitBuilder.apiService.getUser(userId)
            }

        }.asLiveData()
    }


}