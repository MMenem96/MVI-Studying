package com.studying.mvistudyingapp.ui.main.state

import com.studying.mvistudyingapp.model.BlogPost
import com.studying.mvistudyingapp.model.User

data class MainViewState(
    var blogPosts: List<BlogPost>? = null,
    var user: User? = null
)
