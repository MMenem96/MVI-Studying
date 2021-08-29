package com.studying.mvistudyingapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.studying.mvistudyingapp.model.BlogPost
import com.studying.mvistudyingapp.model.User
import com.studying.mvistudyingapp.repository.MainRepository
import com.studying.mvistudyingapp.ui.main.state.MainStateEvent
import com.studying.mvistudyingapp.ui.main.state.MainStateEvent.*
import com.studying.mvistudyingapp.ui.main.state.MainViewState
import com.studying.mvistudyingapp.util.AbsentLiveData
import com.studying.mvistudyingapp.util.DataState

class MainViewModel : ViewModel() {

    private val _stateEvent: MutableLiveData<MainStateEvent> = MutableLiveData()
    private val _viewState: MutableLiveData<MainViewState> = MutableLiveData()

    val viewState: LiveData<MainViewState>
        get() = _viewState

    val dataState: LiveData<DataState<MainViewState>> = Transformations
        .switchMap(_stateEvent) { stateEvent ->
            stateEvent?.let {
                handleStateEvent(stateEvent)
            }
        }

    fun handleStateEvent(stateEvent: MainStateEvent): LiveData<DataState<MainViewState>> {
        println("DEBUG: New StateEvent detected: $stateEvent")
        when (stateEvent) {
            is GetBlogPostEvent -> {
                return MainRepository.getBlogPosts()
            }

            is GetUserEvent -> {
                return MainRepository.getUser(stateEvent.userId)

            }

            is None -> {
                return AbsentLiveData.create()
            }
            else -> {
                return AbsentLiveData.create()
            }
        }
    }

    fun setBlogListData(blogPosts: List<BlogPost>) {
        val update = getCurrentViewStateOrNew()
        update.blogPosts = blogPosts
        _viewState.value = update
    }

    fun setUser(user: User) {
        val update = getCurrentViewStateOrNew()
        update.user = user
        _viewState.value = update
    }


    private fun getCurrentViewStateOrNew(): MainViewState {
        val value = viewState.value ?: MainViewState()
        return value
    }


    fun setStateEvent(event: MainStateEvent) {
        _stateEvent.value = event
    }
}